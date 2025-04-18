package com.reptrack.api.service;

import com.reptrack.api.model.WorkoutExercise;
import com.reptrack.api.model.WorkoutLog;
import com.reptrack.api.repository.WorkoutExerciseRepository;
import com.reptrack.api.repository.WorkoutLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutLogRepository workoutLogRepository;

    @Transactional
    public void updateWorkoutExercise(Long id, WorkoutExercise updated) {
        WorkoutExercise existing = workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("WorkoutExercise not found"));

        if (updated.getName() != null) {
            existing.setName(updated.getName());
        }

        if (updated.getSets() != null && !updated.getSets().isEmpty()) {
            existing.setSets(updated.getSets());
        }
    }

    @Transactional
    public void deleteWorkoutExercise(Long id) {
        workoutExerciseRepository.deleteById(id);
    }

    @Transactional
    public void addWorkoutExerciseToLog(Long logId, WorkoutExercise exercise) {
        WorkoutLog log = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalStateException("WorkoutLog not found"));

        exercise.setLog(log);
        workoutExerciseRepository.save(exercise);
    }
}

