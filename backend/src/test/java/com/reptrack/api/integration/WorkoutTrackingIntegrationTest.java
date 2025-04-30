package com.reptrack.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reptrack.api.dto.CreateWorkoutLogDTO;
import com.reptrack.api.model.Exercise;
import com.reptrack.api.model.WorkoutExercise;
import com.reptrack.api.model.WorkoutLog;
import com.reptrack.api.security.auth.AuthenticationRequest;
import com.reptrack.api.security.auth.AuthenticationResponse;
import com.reptrack.api.security.auth.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkoutTrackingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String authToken;

    @Test
    void completeWorkoutTrackingFlow() throws Exception {
        // 1. Register a new user
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("Test")
                .lastname("User")
                .email("test@example.com")
                .password("password123")
                .build();

        MvcResult registerResult = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        AuthenticationResponse authResponse = objectMapper.readValue(
                registerResult.getResponse().getContentAsString(),
                AuthenticationResponse.class);
        authToken = authResponse.getToken();

        // 2. Create a new exercise
        Exercise exercise = new Exercise();
        exercise.setName("Bench Press");
        exercise.setDescription("Chest exercise");
        exercise.setEquipmentName("Barbell");
        exercise.setMusclesTargeted("Chest, Triceps");

        MvcResult exerciseResult = mockMvc.perform(post("/api/v1/exercises")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exercise)))
                .andExpect(status().isCreated())
                .andReturn();

        Exercise savedExercise = objectMapper.readValue(
                exerciseResult.getResponse().getContentAsString(),
                Exercise.class);

        // 3. Create a workout log
        CreateWorkoutLogDTO workoutLog = new CreateWorkoutLogDTO();
        workoutLog.setName("Morning Workout");
        workoutLog.setDescription("Chest day");
        workoutLog.setDate(LocalDate.now());
        workoutLog.setExercises(new ArrayList<>());

        MvcResult workoutLogResult = mockMvc.perform(post("/api/v1/workout-logs")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutLog)))
                .andExpect(status().isCreated())
                .andReturn();

        WorkoutLog savedWorkoutLog = objectMapper.readValue(
                workoutLogResult.getResponse().getContentAsString(),
                WorkoutLog.class);

        // 4. Add exercise to workout log
        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setName("Bench Press");
        workoutExercise.setSets(new ArrayList<>());

        MvcResult workoutExerciseResult = mockMvc.perform(post("/api/v1/workout-exercises/log/" + savedWorkoutLog.getId())
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutExercise)))
                .andExpect(status().isCreated())
                .andReturn();

        // Get the updated workout log to get the exercise ID
        MvcResult updatedLogResult = mockMvc.perform(get("/api/v1/workout-logs/" + savedWorkoutLog.getId())
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andReturn();

        WorkoutLog updatedLog = objectMapper.readValue(
                updatedLogResult.getResponse().getContentAsString(),
                WorkoutLog.class);
        
        Long workoutExerciseId = updatedLog.getExercises().get(0).getId();

        // 6. Update workout exercise
        workoutExercise.setId(workoutExerciseId);
        workoutExercise.setName("Incline Bench Press");

        mockMvc.perform(patch("/api/v1/workout-exercises/" + workoutExerciseId)
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutExercise)))
                .andExpect(status().isOk());

        // 7. Verify update
        mockMvc.perform(get("/api/v1/workout-logs/" + savedWorkoutLog.getId())
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exercises[0].name").value("Incline Bench Press"));

        // 8. Delete workout exercise
        mockMvc.perform(delete("/api/v1/workout-exercises/" + workoutExerciseId)
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());

        // 9. Delete workout log
        mockMvc.perform(delete("/api/v1/workout-logs/" + savedWorkoutLog.getId())
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
} 