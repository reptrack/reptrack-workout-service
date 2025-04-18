package com.reptrack.api.controller;

import com.reptrack.api.model.WorkoutExercise;
import com.reptrack.api.service.WorkoutExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workout-exercises")
@RequiredArgsConstructor
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateWorkoutExercise(
            @PathVariable Long id,
            @RequestBody WorkoutExercise updatedExercise
    ) {
        workoutExerciseService.updateWorkoutExercise(id, updatedExercise);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutExercise(@PathVariable Long id) {
        workoutExerciseService.deleteWorkoutExercise(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/log/{logId}")
    public ResponseEntity<Void> addWorkoutExerciseToLog(
            @PathVariable Long logId,
            @RequestBody WorkoutExercise newExercise
    ) {
        workoutExerciseService.addWorkoutExerciseToLog(logId, newExercise);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}