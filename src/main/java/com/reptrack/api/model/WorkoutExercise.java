package com.reptrack.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    public WorkoutExercise() {
    }

    public WorkoutExercise(String name, List<WorkoutSet> sets) {
        this.name = name;
        this.sets = sets;
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

    public List<WorkoutSet> getSets() {
        return sets;
    }

    public void setSets(List<WorkoutSet> sets) {
        this.sets = sets;
    }

    public void setLog(WorkoutLog log) {
        this.log = log;
    }

    public WorkoutLog getLog() {
        return log;
    }

    @Override
    public String toString() {
        return "WorkoutExercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sets=" + sets +
                ", log=" + log +
                '}';
    }

    @Embeddable
    public static class WorkoutSet {
        private int reps;
        private float weight;
        private boolean warmup;

        public WorkoutSet() {
        }

        public WorkoutSet(int reps, float weight, boolean warmup) {
            this.reps = reps;
            this.weight = weight;
            this.warmup = warmup;
        }

        public int getReps() {
            return reps;
        }

        public void setReps(int reps) {
            this.reps = reps;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public boolean isWarmup() {
            return warmup;
        }

        public void setWarmup(boolean warmup) {
            this.warmup = warmup;
        }

        @Override
        public String toString() {
            return "WorkoutSet{" +
                    "reps=" + reps +
                    ", weight=" + weight +
                    ", warmup=" + warmup +
                    '}';
        }
    }
}
