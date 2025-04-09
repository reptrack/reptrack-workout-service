package com.reptrack.api.service;

import com.reptrack.api.model.WorkoutLog;
import com.reptrack.api.repository.WorkoutLogRepository;
import com.reptrack.api.security.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reptrack.api.security.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

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

    public void deleteWorkoutLog(Long workoutlogId, String email) {
        WorkoutLog workoutLog = workoutLogRepository.findById(workoutlogId)
                .orElseThrow(() -> new IllegalStateException(
                        "Workout log with id " + workoutlogId + " does not exist"));

        if (!workoutLog.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
        }

        workoutLogRepository.deleteById(workoutlogId);
    }

    @Transactional
    public void updateWorkoutLog(
            Long workoutlogId,
            String name,
            String description,
            String email) {
        WorkoutLog workoutLog= workoutLogRepository.findById(workoutlogId)
                .orElseThrow(() -> new IllegalStateException(
                        "workout log with id " + workoutlogId + " does not exist"));

        if (!workoutLog.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
        }

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(workoutLog.getName(), name)) {
            workoutLog.setName(name);
        }

        if (description != null &&
                description.length() > 0 &&
                !Objects.equals(workoutLog.getDescription(), description)) {
            workoutLog.setDescription(description);
        }
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
}
