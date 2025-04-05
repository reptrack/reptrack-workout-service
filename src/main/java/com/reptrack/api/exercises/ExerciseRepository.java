package com.reptrack.api.exercises;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository
        extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findExerciseByName(String name);
}
