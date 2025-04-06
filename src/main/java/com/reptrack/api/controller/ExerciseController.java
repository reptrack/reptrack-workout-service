package com.reptrack.api.controller;

import com.reptrack.api.model.Exercise;
import com.reptrack.api.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getExercises() {
        return ResponseEntity.ok(exerciseService.getExercises());
    }

    @PostMapping
    public ResponseEntity<Exercise> registerNewExercise(@RequestBody Exercise exercise) {
        Exercise saved = exerciseService.addNewExercise(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping(path = "{exerciseId}")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable("exerciseId") Long exerciseId){
        exerciseService.deleteExercise(exerciseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{exerciseId}")
    public ResponseEntity<String> updateExercise(
            @PathVariable("exerciseId") Long exerciseId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        exerciseService.updateExercise(exerciseId, name, description);
        return ResponseEntity.ok("Exercise updated");
    }
}
