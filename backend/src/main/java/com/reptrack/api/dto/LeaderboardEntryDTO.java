package com.reptrack.api.dto;

import lombok.Getter;

@Getter
public class LeaderboardEntryDTO {
    private String email;
    private String exercise;
    private int weight;
    private int reps;
    private int sets;

    public LeaderboardEntryDTO(String email, String exercise, int weight, int reps, int sets) {
        this.email = email;
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }
}
