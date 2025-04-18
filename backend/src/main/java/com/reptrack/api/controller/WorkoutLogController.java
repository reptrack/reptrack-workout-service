package com.reptrack.api.controller;

import com.reptrack.api.security.user.User;
import com.reptrack.api.service.WorkoutLogService;
import com.reptrack.api.model.WorkoutLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<WorkoutLog> logs = workoutLogService.getWorkoutLogsForUser(email);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutLog> getWorkoutLogById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        WorkoutLog log = workoutLogService.getWorkoutLogById(id, user.getUsername());  // Use getUsername()
        return ResponseEntity.ok(log);
    }

    @PostMapping
    public ResponseEntity<WorkoutLog> registerNewWorkoutLog(@RequestBody WorkoutLog workoutLog) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        WorkoutLog saved = workoutLogService.addNewWorkoutLogForUser(workoutLog, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping(path = "{workoutlogId}")
    public ResponseEntity<Void> deleteWorkoutLog(
            @PathVariable("workoutlogId") Long workoutlogId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        workoutLogService.deleteWorkoutLog(workoutlogId, email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{workoutlogId}")
    public ResponseEntity<WorkoutLog> updateWorkoutLog(
            @PathVariable("workoutlogId") Long workoutlogId,
            @RequestBody WorkoutLog updatedLog) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        WorkoutLog updated = workoutLogService.updateWorkoutLog(workoutlogId, updatedLog, email);
        return ResponseEntity.ok(updated);

    }
}
