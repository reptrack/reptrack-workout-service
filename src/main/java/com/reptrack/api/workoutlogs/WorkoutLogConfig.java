package com.reptrack.api.workoutlogs;

import com.reptrack.api.exercises.Exercise;
import com.reptrack.api.exercises.ExerciseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class WorkoutLogConfig {

    @Bean
    @Order(2)
    CommandLineRunner workoutLogLineRunner(
            WorkoutLogRepository workoutLogRepository,
            ExerciseRepository exerciseRepository) {
        return args -> {

            Exercise barbell = exerciseRepository.findExerciseByName("Barbell Bench Press")
                    .orElseThrow(() -> new IllegalStateException("Barbell Bench Press not found"));

            Exercise dumbbell = exerciseRepository.findExerciseByName("Dumbbell Bench Press")
                    .orElseThrow(() -> new IllegalStateException("Dumbbell Bench Press not found"));

            WorkoutExercise barbellWorkout = new WorkoutExercise(
                    barbell.getName(),
                    List.of(
                            new WorkoutExercise.WorkoutSet(10, 45, true),
                            new WorkoutExercise.WorkoutSet(8, 135, true),
                            new WorkoutExercise.WorkoutSet(6, 185, false)
                    )
            );

            WorkoutExercise dumbbellWorkout = new WorkoutExercise(
                    dumbbell.getName(),
                    List.of(
                            new WorkoutExercise.WorkoutSet(12, 40, false),
                            new WorkoutExercise.WorkoutSet(10, 50, false)
                    )
            );

            WorkoutLog log = new WorkoutLog(
                    "Chest Day",
                    LocalDate.now(),
                    "Focused on heavy pressing"
            );

            log.addExercise(barbellWorkout);
            log.addExercise(dumbbellWorkout);

            workoutLogRepository.save(log);
        };
    };
}
