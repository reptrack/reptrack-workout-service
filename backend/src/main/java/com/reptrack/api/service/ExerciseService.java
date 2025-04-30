package com.reptrack.api.service;

import com.reptrack.api.model.Exercise;
import com.reptrack.api.repository.ExerciseRepository;
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

    public void deleteExercise(Long id) {
        boolean exists = exerciseRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("exercise with id " + id +" does not exists");
        }
        exerciseRepository.deleteById(id);
    }

    @Transactional
    public void updateExercise(Long id,
                                String name,
                                String description) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "exercise with id " + id + " does not exist"));

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

    @Transactional
    public void partialUpdateExercise(Long id, Exercise incoming) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Exercise with id " + id + " does not exist"));

        if (incoming.getName() != null && !incoming.getName().isBlank()) {
            exercise.setName(incoming.getName());
        }

        if (incoming.getDescription() != null && !incoming.getDescription().isBlank()) {
            exercise.setDescription(incoming.getDescription());
        }

        if (incoming.getEquipmentName() != null && !incoming.getEquipmentName().isBlank()) {
            exercise.setEquipmentName(incoming.getEquipmentName());
        }

        if (incoming.getMusclesTargeted() != null && !incoming.getMusclesTargeted().isBlank()) {
            exercise.setMusclesTargeted(incoming.getMusclesTargeted());
        }
    }

    @Transactional
    public void approveExercise(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Exercise with id " + id + " does not exist"));

        exercise.setApproved(true);
    }

}
