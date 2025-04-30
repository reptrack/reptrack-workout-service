package com.reptrack.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateWorkoutExerciseDTO {
    @NotBlank
    private String name;

    @NotNull
    private List<WorkoutSetDTO> sets;
}
