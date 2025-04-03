package com.reptrack.api.exercises;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Exercise> getExercises() {
        return exerciseService.getExercises();
    }

    @PostMapping
    public void registerNewExercise(@RequestBody Exercise exercise) {
        exerciseService.addNewExercise(exercise);
    }

    @DeleteMapping(path = "{exerciseId}")
    public void deleteExercise(
            @PathVariable("exerciseId") Long exerciseId){
        exerciseService.deleteExercise(exerciseId);
    }

    @PutMapping(path = "{exerciseId}")
    public void updateExercise(
            @PathVariable("exerciseId") Long exerciseId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
                exerciseService.updateExercise(exerciseId, name, description);
    }
}
