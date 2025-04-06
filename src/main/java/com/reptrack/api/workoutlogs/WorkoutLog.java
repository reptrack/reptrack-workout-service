package com.reptrack.api.workoutlogs;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    private String name;
    private LocalDate date;
    private String description;

    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WorkoutExercise> exercises = new ArrayList<>();

    public WorkoutLog() {
    }

    // When you're creating a log and adding exercises later via addExercise(...) or setExercises(...)
    public WorkoutLog(String name, LocalDate date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    // When you're manually assigning an ID
    public WorkoutLog(Long id, String name, List<WorkoutExercise> exercises, LocalDate date, String description) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
        this.date = date;
        this.description = description;
    }

    // When you're creating a log with exercises already assigned
    public WorkoutLog(String name, List<WorkoutExercise> exercises, LocalDate date, String description) {
        this.name = name;
        this.exercises = exercises;
        this.date = date;
        this.description = description;
    }

    public void addExercise(WorkoutExercise exercise) {
        exercise.setLog(this);
        this.exercises.add(exercise);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkoutExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutExercise> exercises) {
        this.exercises = exercises;
        if (exercises != null) {
            for (WorkoutExercise exercise : exercises) {
                exercise.setLog(this);
            }
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WorkoutLog{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", exercises=" + exercises +
                '}';
    }
}
