package com.reptrack.api.config;

import com.reptrack.api.model.Exercise;
import com.reptrack.api.repository.ExerciseRepository;
import com.reptrack.api.model.WorkoutExercise;
import com.reptrack.api.model.WorkoutLog;
import com.reptrack.api.repository.WorkoutLogRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import com.reptrack.api.security.user.User;
import com.reptrack.api.security.user.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class WorkoutLogConfig {

    @Bean
    @Order(2)
    CommandLineRunner workoutLogLineRunner(
            WorkoutLogRepository workoutLogRepository,
            ExerciseRepository exerciseRepository,
            UserRepository userRepository) {
        return args -> {

            User user = userRepository.findByEmail("demo@example.com")
                    .orElseThrow(() -> new IllegalStateException("User 'demo@example.com' not found"));

            Exercise barbell = exerciseRepository.findExerciseByName("Barbell Bench Press")
                    .orElseThrow(() -> new IllegalStateException("Barbell Bench Press not found"));

            Exercise dumbbell = exerciseRepository.findExerciseByName("Dumbbell Bench Press")
                    .orElseThrow(() -> new IllegalStateException("Dumbbell Bench Press not found"));

            WorkoutExercise barbellWorkout = new WorkoutExercise();
            barbellWorkout.setName(barbell.getName());
            barbellWorkout.setSets(List.of(
                    new WorkoutExercise.WorkoutSet(10, 45, true),
                    new WorkoutExercise.WorkoutSet(8, 135),
                    new WorkoutExercise.WorkoutSet(6, 185)
            ));

            WorkoutExercise dumbbellWorkout = new WorkoutExercise();
            dumbbellWorkout.setName(dumbbell.getName());
            dumbbellWorkout.setSets(List.of(
                    new WorkoutExercise.WorkoutSet(12, 40, true),
                    new WorkoutExercise.WorkoutSet(10, 50)
            ));


            WorkoutLog log = new WorkoutLog();
            log.setName("Chest Day");
            log.setDate(LocalDate.now());
            log.setDescription("Focused on heavy pressing");
            log.setUser(user);
            log.addExercise(barbellWorkout);
            log.addExercise(dumbbellWorkout);
            workoutLogRepository.save(log);
        };
    };
}
