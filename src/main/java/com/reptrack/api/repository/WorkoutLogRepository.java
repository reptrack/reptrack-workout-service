package com.reptrack.api.repository;

import com.reptrack.api.model.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutLogRepository
        extends JpaRepository<WorkoutLog, Long> {

    Optional<WorkoutLog> findWorkoutLogByName(String name);
}
