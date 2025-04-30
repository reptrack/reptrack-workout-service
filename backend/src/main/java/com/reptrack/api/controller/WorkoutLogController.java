package com.reptrack.api.controller;

import com.reptrack.api.dto.CreateWorkoutLogDTO;
import com.reptrack.api.model.WorkoutExercise;
import com.reptrack.api.security.user.User;
import com.reptrack.api.service.WorkoutLogService;
import com.reptrack.api.model.WorkoutLog;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @GetMapping("/{id}/exercises")
    public ResponseEntity<List<WorkoutExercise>> getExercisesForLog(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<WorkoutExercise> exercises = workoutLogService.getExercisesForLog(id, email);
        return ResponseEntity.ok(exercises);
    }

    @PostMapping
    public ResponseEntity<WorkoutLog> registerNewWorkoutLog(@Valid @RequestBody CreateWorkoutLogDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        WorkoutLog log = workoutLogService.fromDto(dto, email);
        WorkoutLog saved = workoutLogService.save(log);
        URI location = URI.create("/api/v1/workout-logs/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteWorkoutLog(
            @PathVariable("id") Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        workoutLogService.deleteWorkoutLog(id, email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<WorkoutLog> updateWorkoutLog(
            @PathVariable("id") Long id,
            @RequestBody WorkoutLog updatedLog) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        WorkoutLog updated = workoutLogService.updateWorkoutLog(id, updatedLog, email);
        return ResponseEntity.ok(updated);

    }
}
