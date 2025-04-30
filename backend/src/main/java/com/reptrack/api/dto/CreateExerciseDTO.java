package com.reptrack.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateExerciseDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String equipmentName;

    @NotBlank
    private String musclesTargeted;

    @Size(max = 255)
    private String description;
}
