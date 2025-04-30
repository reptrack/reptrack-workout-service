package com.reptrack.api.repository;

import com.reptrack.api.model.WorkoutLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutLogRepository
        extends JpaRepository<WorkoutLog, Long> {

    List<WorkoutLog> findByUserEmail(String email);
    Page<WorkoutLog> findByUserEmail(String email, Pageable pageable);
}
