package com.reptrack.api.service;

import com.reptrack.api.model.WorkoutLog;
import com.reptrack.api.repository.WorkoutLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;

    @Autowired
    public WorkoutLogService(WorkoutLogRepository workoutLogRepository) {
        this.workoutLogRepository = workoutLogRepository;
    }

    public List<WorkoutLog> getWorkoutLogs() {
        return workoutLogRepository.findAll();
    }

    public WorkoutLog addNewWorkoutLog(WorkoutLog workoutLog) {
        Optional<WorkoutLog> workoutLogOptional = workoutLogRepository.
                findWorkoutLogByName(workoutLog.getName());
        if (workoutLogOptional.isPresent()) {
            throw new IllegalStateException("Workout Log already added");
        }
        return workoutLogRepository.save(workoutLog);
    }

    public void deleteWorkoutLog(Long workoutlogId) {
        boolean exists = workoutLogRepository.existsById(workoutlogId);
        if (!exists) {
            throw new IllegalStateException("workout log with id " + workoutlogId + " does not exist");
        }
        workoutLogRepository.deleteById(workoutlogId);
    }

    @Transactional
    public void updateWorkoutLog(Long workoutlogId,
                               String name,
                               String description) {
        WorkoutLog workoutLog= workoutLogRepository.findById(workoutlogId)
                .orElseThrow(() -> new IllegalStateException(
                        "workout log with id " + workoutlogId + " does not exist"));

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
}
