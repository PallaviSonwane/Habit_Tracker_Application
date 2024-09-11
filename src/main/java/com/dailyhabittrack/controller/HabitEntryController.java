package com.dailyhabittrack.controller;

import com.dailyhabittrack.constant.enums.HabitResponseMessage;
import com.dailyhabittrack.entity.HabitsHabitEntryJoin;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;
import com.dailyhabittrack.service.HabitEntryService;
import jakarta.validation.Valid;

import java.util.List;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/habit-entries")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class HabitEntryController {

    private static final Logger logger = LoggerFactory.getLogger(HabitEntryController.class);

    private final HabitEntryService habitEntryService;

    public HabitEntryController(HabitEntryService habitEntryService) {
        this.habitEntryService = habitEntryService;
    }

    @PostMapping
    public ResponseEntity<HabitEntryResponse> createHabitEntry(@Valid @RequestBody HabitEntryRequest request) {
        logger.info("Habit Entry create request received: {}", request);
        HabitEntryResponse response = habitEntryService.createHabitEntry(request);
        logger.info("Habit Entry created: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{entry-id}")
    public ResponseEntity<String> deleteHabitEntry(@PathVariable("entry-id") Long entryId) {
        logger.info("Delete request for Habit Entry id: {}", entryId);
        habitEntryService.deleteHabitEntry(entryId);
        logger.info("Habit Entry deleted for id: {}", entryId);
        return ResponseEntity.ok(HabitResponseMessage.HABIT_SUCCESSFULLY_DELETED.getMessage(entryId));
    }

    @PutMapping("/entry-update")
    public ResponseEntity<HabitEntryResponse> updateHabitEntry(@RequestBody HabitEntryRequest updateRequest) {
        logger.info("Update request for Habit Entry id: {}", updateRequest);
        HabitEntryResponse updatedEntry = habitEntryService.updateHabitValue(updateRequest);
        logger.info("Habit Entry updated for id: {}", updatedEntry);
        return ResponseEntity.ok(updatedEntry);
    }
    

    @GetMapping("/all/{habitId}")
    public List<HabitsHabitEntryJoin> getHabitEntriesByHabitId(@PathVariable Long habitId) {
        return habitEntryService.getHabitEntriesByHabitId(habitId);
    }

    @GetMapping("/{date}")
    public List<HabitsHabitEntryJoin> getHabitEntriesByDate(@PathVariable LocalDate date) {
        return habitEntryService.getHabitEntriesByDate(date);
    }

    // @GetMapping("/{date}")
    // public HabitsHabitEntryJoin getHabitEntriesByDateAndHabitId(@PathVariable LocalDate date,Long habitId) {
    //     return habitEntryService.getHabitEntriesByDateAndHabitId(date,habitId);
    // }

}
