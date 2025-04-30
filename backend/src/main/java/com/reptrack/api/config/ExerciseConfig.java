package com.reptrack.api.config;

import com.reptrack.api.repository.ExerciseRepository;
import com.reptrack.api.model.Exercise;
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
            Exercise barbellbenchPress = new Exercise();
            barbellbenchPress.setName("Barbell Bench Press");
            barbellbenchPress.setEquipmentName("Bench");
            barbellbenchPress.setMusclesTargeted("Chest, Triceps, Front Delts");
            barbellbenchPress.setDescription("Test Description");

            Exercise dumbbellbenchPress = new Exercise();
            dumbbellbenchPress.setName("Dumbbell Bench Press");
            dumbbellbenchPress.setEquipmentName("Bench");
            dumbbellbenchPress.setMusclesTargeted("Chest, Triceps, Front Delts");
            dumbbellbenchPress.setDescription("Test Description 2");

            repository.saveAll(List.of(barbellbenchPress, dumbbellbenchPress));
        };
    }
}
