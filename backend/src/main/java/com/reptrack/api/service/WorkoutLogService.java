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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;
    private final UserRepository userRepository;

    @Autowired
    public WorkoutLogService(
            WorkoutLogRepository workoutLogRepository
            , UserRepository userRepository) {
        this.workoutLogRepository = workoutLogRepository;
        this.userRepository = userRepository;
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

        return workoutLogRepository.save(existing);
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
        log.setCompleted(false);
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
                .map(s -> new WorkoutExercise.WorkoutSet(s.getReps(), s.getWeight(), s.isWarmup(), s.isCompleted()))
                .collect(Collectors.toList()));
        return exercise;
    }

    public WorkoutLog save(WorkoutLog log) {
        return workoutLogRepository.save(log);
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
