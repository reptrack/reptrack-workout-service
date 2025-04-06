package com.reptrack.api.controller;

import com.reptrack.api.service.WorkoutLogService;
import com.reptrack.api.model.WorkoutLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/workout-logs")
public class WorkoutLogController {

    private final WorkoutLogService workoutLogService;

    @Autowired
    public WorkoutLogController(WorkoutLogService workoutLogService) {
        this.workoutLogService = workoutLogService;
    }

    @GetMapping
    public ResponseEntity<List<WorkoutLog>> getWorkoutLogs() {
        return ResponseEntity.ok(workoutLogService.getWorkoutLogs());
    }

    @PostMapping
    public ResponseEntity<WorkoutLog> registerNewWorkoutLog(@RequestBody WorkoutLog workoutLog) {
        WorkoutLog saved = workoutLogService.addNewWorkoutLog(workoutLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping(path = "{workoutlogId}")
    public ResponseEntity<Void> deleteWorkoutLog(
            @PathVariable("workoutlogId") Long workoutlogId) {
        workoutLogService.deleteWorkoutLog(workoutlogId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{workoutlogId}")
    public ResponseEntity<String> updateWorkoutLog(
            @PathVariable("workoutlogId") Long workoutlogId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        workoutLogService.updateWorkoutLog(workoutlogId, name, description);
        return ResponseEntity.ok("Workout log updated");

    }
}
