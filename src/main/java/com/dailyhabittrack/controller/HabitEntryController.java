package com.dailyhabittrack.controller;

import com.dailyhabittrack.constant.enums.HabitResponseMessage;
import com.dailyhabittrack.entity.HabitsHabitEntryJoin;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;
import com.dailyhabittrack.service.HabitEntryService;
import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/habit-entries")
@CrossOrigin(origins = "*")
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

    // @GetMapping("/habit/{habit-id}")
    // public ResponseEntity<List<HabitEntryResponse>> getEntriesByHabitId(@PathVariable("habit-id") Long habitId) {
    //     logger.info("Fetching Habit Entries for habit id: {}", habitId);
    //     List<HabitEntryResponse> entries = habitEntryService.getEntriesByHabitId(habitId);
    //     logger.info("Fetched Habit Entries for habit id: {}", habitId);
    //     return ResponseEntity.ok(entries);
    // }

    @DeleteMapping("/{entry-id}")
    public ResponseEntity<String> deleteHabitEntry(@PathVariable("entry-id") Long entryId) {
        logger.info("Delete request for Habit Entry id: {}", entryId);
        habitEntryService.deleteHabitEntry(entryId);
        logger.info("Habit Entry deleted for id: {}", entryId);
        return ResponseEntity.ok(HabitResponseMessage.HABIT_SUCCESSFULLY_DELETED.getMessage(entryId));
    }

    @PutMapping("/habit-entries")
    public ResponseEntity<Void> updateHabitEntry(@RequestBody HabitEntryRequest updateRequest) {
        try {
            habitEntryService.updateHabitValue(updateRequest.getHabitId(), updateRequest.getValue());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all/{habitId}")
    public List<HabitsHabitEntryJoin> getHabitEntriesByHabitId(@PathVariable Long habitId) {
        return habitEntryService.getHabitEntriesByHabitId(habitId);
    }
}
