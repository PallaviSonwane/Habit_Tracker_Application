package com.dailyhabittrack.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dailyhabittrack.constant.enums.HabitResponseMessage;
import com.dailyhabittrack.request.HabitRequest;
import com.dailyhabittrack.response.HabitResponse;
import com.dailyhabittrack.service.HabitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/habit")
@CrossOrigin(origins = "*")
public class HabitController {
    private static final Logger logger = LoggerFactory.getLogger(HabitController.class);

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(@Valid @RequestBody HabitRequest habitRequest) {
        logger.info("Habit create request received: {}", habitRequest.getUnitName());
        HabitResponse creationResponse = habitService.createHabit(habitRequest);
        logger.info("Habit created: {}", creationResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(creationResponse);
    }

    @GetMapping("/habits")
    public ResponseEntity<List<HabitResponse>> getAllHabits() {
        List<HabitResponse> habitList = habitService.getAllHabits();
        logger.info("Fetched all habits: {}", habitList);
        return ResponseEntity.ok(habitList);
    }

    @GetMapping("/{habit-id}")
    public ResponseEntity<HabitResponse> getHabit(@PathVariable("habit-id") Long habitId) {
        logger.info("Fetching habit data for id: {}", habitId);
        HabitResponse habitData = habitService.getHabit(habitId);
        logger.info("Fetched habit data for id: {}", habitId);
        return ResponseEntity.ok(habitData);
    }

    @PutMapping("/{habit-id}")
    public ResponseEntity<String> updateHabit(@Valid @PathVariable("habit-id") Long habitId,
            @RequestBody HabitRequest updatedHabit) {
        logger.info("Update request for habit id {} with details: {}", habitId, updatedHabit);
        habitService.updateHabit(updatedHabit, habitId);
        logger.info("Habit updated for id: {}", habitId);
        return ResponseEntity.ok(HabitResponseMessage.HABIT_SUCCESSFULLY_UPDATED.getMessage(habitId));
    }

    @DeleteMapping("/{habit-id}")
    public ResponseEntity<String> deleteHabit(@PathVariable("habit-id") Long habitId) {
        logger.info("Delete request for habit id: {}", habitId);
        habitService.deleteHabit(habitId);
        logger.info("Habit deleted for id: {}", habitId);
        return ResponseEntity.ok(HabitResponseMessage.HABIT_SUCCESSFULLY_DELETED.getMessage(habitId));
    }

    @GetMapping("/habits/date/{date}")
    public ResponseEntity<List<HabitResponse>> getHabitsByDate(@PathVariable("date") LocalDate date) {
        logger.info("Fetching habits for date: {}", date);
        List<HabitResponse> habitList = habitService.getHabitsByDate(date);
        logger.info("Fetched habits for date: {}", date);
        return ResponseEntity.ok(habitList);
    }

    @GetMapping("/daily-progress")
    public ResponseEntity<Map<String, Map<String, Integer>>> getMonthlyProgressForAllHabits(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        logger.info("Fetching daily progress for date range: {} to {}", startDate, endDate);
        try {
            Map<String, Map<String, Integer>> progressData = habitService.getMonthlyProgressForAllHabits(startDate, endDate);
            logger.info("Fetched daily progress data");
            return ResponseEntity.ok(progressData);
        } catch (Exception e) {
            logger.error("Error fetching data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
