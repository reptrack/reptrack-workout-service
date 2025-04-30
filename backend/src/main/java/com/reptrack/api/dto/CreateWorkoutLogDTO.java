package com.reptrack.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateWorkoutLogDTO {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private LocalDate date;

    private List<CreateWorkoutExerciseDTO> exercises;
}
