package com.reptrack.api.controller;

import com.reptrack.api.model.Exercise;
import com.reptrack.api.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseControllerTest {

    @Mock
    private ExerciseService exerciseService;

    @InjectMocks
    private ExerciseController exerciseController;

    private Exercise testExercise;
    private List<Exercise> testExercises;

    @BeforeEach
    void setUp() {
        testExercise = new Exercise();
        testExercise.setId(1L);
        testExercise.setName("Bench Press");
        testExercise.setDescription("Chest exercise");
        testExercise.setEquipmentName("Barbell");
        testExercise.setMusclesTargeted("Chest, Triceps");
        testExercise.setApproved(true);

        testExercises = Arrays.asList(testExercise);
    }

    @Test
    void getExercises_ShouldReturnListOfExercises() {
        when(exerciseService.getExercises()).thenReturn(testExercises);

        ResponseEntity<List<Exercise>> response = exerciseController.getExercises();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testExercises, response.getBody());
        verify(exerciseService, times(1)).getExercises();
    }

    @Test
    void registerNewExercise_ShouldReturnCreatedExercise() {
        when(exerciseService.addNewExercise(any(Exercise.class))).thenReturn(testExercise);

        ResponseEntity<Exercise> response = exerciseController.registerNewExercise(testExercise);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testExercise, response.getBody());
        verify(exerciseService, times(1)).addNewExercise(any(Exercise.class));
    }

    @Test
    void deleteExercise_ShouldReturnNoContent() {
        doNothing().when(exerciseService).deleteExercise(anyLong());

        ResponseEntity<Void> response = exerciseController.deleteExercise(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(exerciseService, times(1)).deleteExercise(1L);
    }

    @Test
    void patchExercise_ShouldReturnOk() {
        doNothing().when(exerciseService).partialUpdateExercise(anyLong(), any(Exercise.class));

        ResponseEntity<Void> response = exerciseController.patchExercise(1L, testExercise);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(exerciseService, times(1)).partialUpdateExercise(1L, testExercise);
    }

    @Test
    void approveExercise_ShouldReturnOk() {
        doNothing().when(exerciseService).approveExercise(anyLong());

        ResponseEntity<Void> response = exerciseController.approveExercise(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(exerciseService, times(1)).approveExercise(1L);
    }
} 