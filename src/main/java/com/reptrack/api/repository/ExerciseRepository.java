package com.reptrack.api.repository;

import com.reptrack.api.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository
        extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findExerciseByName(String name);
}
