package com.reptrack.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout_exercise_seq")
    @SequenceGenerator(
            name = "workout_exercise_seq",
            sequenceName = "workout_exercise_seq",
            allocationSize = 1
    )
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(
            name = "workout_exercise_sets",
            joinColumns = @JoinColumn(name = "workout_exercise_id")
    )
    private List<WorkoutSet> sets = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "log_id")
    @JsonBackReference
    private WorkoutLog log;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class WorkoutSet {
        private int reps;
        private float weight;
        private boolean warmup;
    }
}
