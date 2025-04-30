package com.reptrack.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkoutSetDTO {
    @Min(1)
    private int reps;

    @Min(0)
    private float weight;

    @NotNull
    private boolean warmup;

}
