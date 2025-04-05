package com.reptrack.api.exercises;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class ExerciseConfig {

    @Bean
    @Order(1)
    CommandLineRunner exerciseLineRunner(
            ExerciseRepository repository) {
        return args -> {
            Exercise barbellbenchPress = new Exercise(
                    "Barbell Bench Press",
                    "Bench",
                    "Chest, Triceps, Front Delts",
                    "Test Description",
                    true
                    );

            Exercise dumbbellbenchPress = new Exercise(
                    "Dumbbell Bench Press",
                    "Bench",
                    "Chest, Triceps, Front Delts",
                    "Test Description 2",
                    true
                    );
            repository.saveAll(
                    List.of(barbellbenchPress, dumbbellbenchPress)
            );
        };
    }
}
