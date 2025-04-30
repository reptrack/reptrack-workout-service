package com.reptrack.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.reptrack.api.security.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutLog {

    @Id
    @SequenceGenerator(
            name = "workoutlog_sequence",
            sequenceName = "workoutlog_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workoutlog_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private LocalDate date;
    private String description;

    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WorkoutExercise> exercises = new ArrayList<>();

    public void addExercise(WorkoutExercise exercise) {
        if (exercise != null) {
            exercise.setLog(this);
            this.exercises.add(exercise);
        }
    }

    public void setExercises(List<WorkoutExercise> exercises) {
        this.exercises = exercises;
        if (exercises != null) {
            for (WorkoutExercise exercise : exercises) {
                exercise.setLog(this);
            }
        }
    }
}
