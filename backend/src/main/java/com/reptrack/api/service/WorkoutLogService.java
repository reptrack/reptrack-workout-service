package com.reptrack.api.service;

import com.reptrack.api.dto.CreateWorkoutExerciseDTO;
import com.reptrack.api.dto.CreateWorkoutLogDTO;
import com.reptrack.api.exception.ResourceNotFoundException;
import com.reptrack.api.model.WorkoutExercise;
import com.reptrack.api.model.WorkoutLog;
import com.reptrack.api.repository.WorkoutLogRepository;
import com.reptrack.api.security.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.reptrack.api.security.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutLogService {

    private static final String LEADERBOARD_KEY = "leaderboard:workouts";
    private final WorkoutLogRepository workoutLogRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;


    @Autowired
    public WorkoutLogService(
            WorkoutLogRepository workoutLogRepository,
            UserRepository userRepository,
            RedisTemplate<String, String> redisTemplate) {
        this.workoutLogRepository = workoutLogRepository;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public WorkoutLog getWorkoutLogById(Long id, String email) {
        WorkoutLog log = workoutLogRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout log not found"));

        if (!log.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
        }

        return log;
    }

    public void deleteWorkoutLog(Long id, String email) {
        WorkoutLog workoutLog = workoutLogRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Workout log with id " + id + " does not exist"));

        if (!workoutLog.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
        }

        workoutLogRepository.deleteById(id);
    }

    @Transactional
    public WorkoutLog updateWorkoutLog(
            Long id,
            WorkoutLog newLog,
            String email) {
        WorkoutLog existing = workoutLogRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkoutLog not found"));

        if (!existing.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
        }

        if (newLog.getName() != null) {
            existing.setName(newLog.getName());
        }
        if (newLog.getDescription() != null) {
            existing.setDescription(newLog.getDescription());
        }
        if (newLog.getDate() != null) {
            existing.setDate(newLog.getDate());
        }
        if (newLog.getExercises() != null && !newLog.getExercises().isEmpty()) {
            existing.setExercises(newLog.getExercises());
        }

        WorkoutLog updated = workoutLogRepository.save(existing);

        for (WorkoutExercise ex : updated.getExercises()) {
            String exerciseName = ex.getName();
            String userEmail = updated.getUser().getEmail();

            // Get max set with highest weight for this exercise
            WorkoutExercise.WorkoutSet bestSet = ex.getSets().stream()
                    .max((a, b) -> Float.compare(a.getWeight(), b.getWeight()))
                    .orElse(null);

            if (bestSet == null) continue;

            int totalSets = ex.getSets().size();

            // Create a unique member string
            String redisValue = String.format("%s|%s|%d|%d|%d", userEmail, exerciseName,
                    (int) bestSet.getWeight(), bestSet.getReps(), totalSets);

            redisTemplate.opsForZSet().add("leaderboard:" + exerciseName + ":weight", redisValue, bestSet.getWeight());
            redisTemplate.opsForZSet().add("leaderboard:" + exerciseName + ":reps", redisValue, bestSet.getReps());
            redisTemplate.opsForZSet().add("leaderboard:" + exerciseName + ":sets", redisValue, totalSets);
        }


        return updated;
    }

    public List<WorkoutLog> getWorkoutLogsForUser(String email) {
        return workoutLogRepository.findByUserEmail(email);
    }

    public WorkoutLog addNewWorkoutLogForUser(WorkoutLog workoutLog, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        workoutLog.setUser(user);
        return workoutLogRepository.save(workoutLog);
    }

    public WorkoutLog fromDto(CreateWorkoutLogDTO dto, String email) {
        WorkoutLog log = new WorkoutLog();
        log.setName(dto.getName());
        log.setDescription(dto.getDescription());
        log.setDate(dto.getDate());
        log.setUser(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));

        if (dto.getExercises() != null) {
            List<WorkoutExercise> exercises = dto.getExercises().stream()
                    .map(this::mapToWorkoutExercise)  // expects CreateWorkoutExerciseDTO
                    .collect(Collectors.toList());
            log.setExercises(exercises);
        }

        return log;
    }

    private WorkoutExercise mapToWorkoutExercise(CreateWorkoutExerciseDTO dto) {
        WorkoutExercise exercise = new WorkoutExercise();
        exercise.setName(dto.getName());
        exercise.setSets(dto.getSets().stream()
                .map(s -> new WorkoutExercise.WorkoutSet(s.getReps(), s.getWeight()))
                .collect(Collectors.toList()));
        return exercise;
    }

    public WorkoutLog save(WorkoutLog log) {
        if (log.getUser() == null) {
            throw new IllegalStateException("WorkoutLog user is null before saving.");
        }
        if (log.getUser().getEmail() == null) {
            throw new IllegalStateException("WorkoutLog user email is null before saving.");
        }
        // Optional: log to console
        System.out.println("Saving log for user: " + log.getUser().getEmail());

        WorkoutLog savedLog = workoutLogRepository.save(log);

        for (WorkoutExercise ex : savedLog.getExercises()) {
            String exerciseName = ex.getName();
            String userEmail = savedLog.getUser().getEmail();

            // Get max set with highest weight for this exercise
            WorkoutExercise.WorkoutSet bestSet = ex.getSets().stream()
                    .max((a, b) -> Float.compare(a.getWeight(), b.getWeight()))
                    .orElse(null);

            if (bestSet == null) continue;

            int totalSets = ex.getSets().size();

            // Create a unique member string
            String redisValue = String.format("%s|%s|%d|%d|%d", userEmail, exerciseName,
                    (int) bestSet.getWeight(), bestSet.getReps(), totalSets);

            redisTemplate.opsForZSet().add("leaderboard:" + exerciseName + ":weight", redisValue, bestSet.getWeight());
            redisTemplate.opsForZSet().add("leaderboard:" + exerciseName + ":reps", redisValue, bestSet.getReps());
            redisTemplate.opsForZSet().add("leaderboard:" + exerciseName + ":sets", redisValue, totalSets);
        }


        return savedLog;
    }

    public Page<WorkoutLog> getWorkoutLogsForUser(String email, Pageable pageable) {
        return workoutLogRepository.findByUserEmail(email, pageable);
    }

    public List<WorkoutExercise> getExercisesForLog(Long logId, String email) {
        WorkoutLog log = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout log not found"));

        if (!log.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You do not have permission to view this log's exercises");
        }

        return log.getExercises();
    }
}
