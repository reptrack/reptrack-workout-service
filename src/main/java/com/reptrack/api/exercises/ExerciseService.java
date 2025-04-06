package com.reptrack.api.exercises;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getExercises() {
        return exerciseRepository.findAll();
    }

    public Exercise addNewExercise(Exercise exercise) {
        Optional<Exercise> exerciseOptional = exerciseRepository
                .findExerciseByName(exercise.getName());
        if (exerciseOptional.isPresent()) {
            throw new IllegalStateException("Exercise already added");
        }
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long exerciseId) {
        boolean exists = exerciseRepository.existsById(exerciseId);
        if (!exists) {
            throw new IllegalStateException("exercise with id " + exerciseId +" does not exists");
        }
        exerciseRepository.deleteById(exerciseId);
    }

    @Transactional
    public void updateExercise(Long exerciseId,
                                String name,
                                String description) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalStateException(
                        "exercise with id " + exerciseId + " does not exist"));

        if (name != null &&
            name.length() > 0 &&
            !Objects.equals(exercise.getName(), name)) {
            exercise.setName(name);
        }

        if (description != null &&
            description.length() > 0 &&
            !Objects.equals(exercise.getDescription(), description)) {
            exercise.setDescription(description);
        }
    }
}
