package com.reptrack.api.controller;

import com.reptrack.api.model.WorkoutLog;
import com.reptrack.api.security.user.User;
import com.reptrack.api.service.WorkoutLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutLogControllerTest {

    @Mock
    private WorkoutLogService workoutLogService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private WorkoutLogController workoutLogController;

    private WorkoutLog testWorkoutLog;
    private List<WorkoutLog> testWorkoutLogs;
    private User testUser;
    private static final String TEST_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        // Enable lenient mocking to allow unused stubbings
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn(TEST_EMAIL);
        SecurityContextHolder.setContext(securityContext);

        testUser = new User();
        testUser.setEmail(TEST_EMAIL);

        testWorkoutLog = new WorkoutLog();
        testWorkoutLog.setId(1L);
        testWorkoutLog.setName("Morning Workout");
        testWorkoutLog.setDescription("Chest day");
        testWorkoutLog.setDate(LocalDate.now());
        testWorkoutLog.setUser(testUser);

        testWorkoutLogs = Arrays.asList(testWorkoutLog);
    }

    @Test
    void getWorkoutLogs_ShouldReturnUserWorkoutLogs() {
        when(workoutLogService.getWorkoutLogsForUser(TEST_EMAIL)).thenReturn(testWorkoutLogs);

        ResponseEntity<List<WorkoutLog>> response = workoutLogController.getWorkoutLogs();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testWorkoutLogs, response.getBody());
        verify(workoutLogService).getWorkoutLogsForUser(TEST_EMAIL);
    }

    @Test
    void getWorkoutLogById_ShouldReturnWorkoutLog() {
        when(workoutLogService.getWorkoutLogById(1L, TEST_EMAIL)).thenReturn(testWorkoutLog);

        ResponseEntity<WorkoutLog> response = workoutLogController.getWorkoutLogById(1L, testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testWorkoutLog, response.getBody());
        verify(workoutLogService).getWorkoutLogById(1L, TEST_EMAIL);
    }

    @Test
    void registerNewWorkoutLog_ShouldReturnCreatedWorkoutLog() {
        when(workoutLogService.addNewWorkoutLogForUser(testWorkoutLog, TEST_EMAIL)).thenReturn(testWorkoutLog);

        ResponseEntity<WorkoutLog> response = workoutLogController.registerNewWorkoutLog(testWorkoutLog);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testWorkoutLog, response.getBody());
        verify(workoutLogService).addNewWorkoutLogForUser(testWorkoutLog, TEST_EMAIL);
    }

    @Test
    void deleteWorkoutLog_ShouldReturnNoContent() {
        ResponseEntity<Void> response = workoutLogController.deleteWorkoutLog(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(workoutLogService).deleteWorkoutLog(1L, TEST_EMAIL);
    }

    @Test
    void updateWorkoutLog_ShouldReturnUpdatedWorkoutLog() {
        when(workoutLogService.updateWorkoutLog(1L, testWorkoutLog, TEST_EMAIL)).thenReturn(testWorkoutLog);

        ResponseEntity<WorkoutLog> response = workoutLogController.updateWorkoutLog(1L, testWorkoutLog);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testWorkoutLog, response.getBody());
        verify(workoutLogService).updateWorkoutLog(1L, testWorkoutLog, TEST_EMAIL);
    }
} 