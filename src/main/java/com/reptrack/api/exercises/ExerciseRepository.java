package com.reptrack.api.exercises;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface ExerciseRepository
        extends JpaRepository<Exercise, Long> {

    @Query("SELECT e FROM Exercise e WHERE e.name = ?1")
    Optional<Exercise> findExerciseByName(String name);
}
