package com.reptrack.api.controller;

import com.reptrack.api.model.WorkoutExercise;
import com.reptrack.api.service.WorkoutExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutExerciseControllerTest {

    @Mock
    private WorkoutExerciseService workoutExerciseService;

    @InjectMocks
    private WorkoutExerciseController workoutExerciseController;

    private WorkoutExercise testWorkoutExercise;

    @BeforeEach
    void setUp() {
        testWorkoutExercise = new WorkoutExercise();
        testWorkoutExercise.setId(1L);
        testWorkoutExercise.setName("Bench Press");
        testWorkoutExercise.setSets(new ArrayList<>());
    }

    @Test
    void updateWorkoutExercise_ShouldReturnOk() {
        doNothing().when(workoutExerciseService).updateWorkoutExercise(anyLong(), any(WorkoutExercise.class));

        ResponseEntity<Void> response = workoutExerciseController.updateWorkoutExercise(1L, testWorkoutExercise);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(workoutExerciseService, times(1)).updateWorkoutExercise(1L, testWorkoutExercise);
    }

    @Test
    void deleteWorkoutExercise_ShouldReturnNoContent() {
        doNothing().when(workoutExerciseService).deleteWorkoutExercise(anyLong());

        ResponseEntity<Void> response = workoutExerciseController.deleteWorkoutExercise(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(workoutExerciseService, times(1)).deleteWorkoutExercise(1L);
    }

    @Test
    void addWorkoutExerciseToLog_ShouldReturnCreated() {
        doNothing().when(workoutExerciseService).addWorkoutExerciseToLog(anyLong(), any(WorkoutExercise.class));

        ResponseEntity<Void> response = workoutExerciseController.addWorkoutExerciseToLog(1L, testWorkoutExercise);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(workoutExerciseService, times(1)).addWorkoutExerciseToLog(1L, testWorkoutExercise);
    }
} 