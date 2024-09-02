package com.dailyhabittrack.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dailyhabittrack.constant.enums.HabitEntryResponseMessage;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;
import com.dailyhabittrack.service.HabitEntryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/habit-entry")
@CrossOrigin(origins = "*")
public class HabitEntryController {
    private static final Logger logger = LoggerFactory.getLogger(HabitEntryController.class);

    private final HabitEntryService habitEntryService;

    public HabitEntryController(HabitEntryService habitEntryService) {
        this.habitEntryService = habitEntryService;
    }

    @PostMapping
    public ResponseEntity<HabitEntryResponse> createHabitEntry(@Valid @RequestBody HabitEntryRequest habitEntryRequest) {
        logger.info("Habit entry create request received: {}", habitEntryRequest);
        HabitEntryResponse creationResponse = habitEntryService.createHabitEntry(habitEntryRequest);
        logger.info("Habit entry created: {}", creationResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(creationResponse);
    }

    @GetMapping("/habit-entries")
    public ResponseEntity<List<HabitEntryResponse>> getAllHabitEntries() {
        List<HabitEntryResponse> habitEntryList = habitEntryService.getAllHabitEntries();
        logger.info("Habit entry information fetched for all entries: {}", habitEntryList);
        return ResponseEntity.ok(habitEntryList);
    }

    @GetMapping("/{entry-id}")
    public ResponseEntity<HabitEntryResponse> getHabitEntry(@PathVariable("entry-id") Long entryId) {
        logger.info("Fetching habit entry data for entry id: {}", entryId);
        HabitEntryResponse habitEntryData = habitEntryService.getHabitEntry(entryId);
        logger.info("Habit entry data fetched for entry id: {}", entryId);
        return ResponseEntity.ok(habitEntryData); 
    }

    @PutMapping("/{entry-id}")
    public ResponseEntity<String> updateHabitEntry(@Valid @PathVariable("entry-id") Long entryId,
                                                   @RequestBody HabitEntryRequest updatedHabitEntry) {
        logger.info("Habit entry update request received for entry id {} with details: {}", entryId, updatedHabitEntry);
        habitEntryService.updateHabitEntry(updatedHabitEntry, entryId);
        logger.info("Habit entry updated for entry id: {}", entryId);
        return ResponseEntity.ok()
                .body(HabitEntryResponseMessage.HABIT_ENTRY_SUCCESSFULLY_UPDATED.getMessage(entryId));
    }

    @DeleteMapping("/{entry-id}")
    public ResponseEntity<String> deleteHabitEntry(@PathVariable("entry-id") Long entryId) {
        logger.info("Habit entry delete request received for entry id: {}", entryId);
        habitEntryService.deleteHabitEntry(entryId);
        logger.info("Habit entry deleted for entry id: {}", entryId);
        return ResponseEntity.ok()
                .body(HabitEntryResponseMessage.HABIT_ENTRY_SUCCESSFULLY_DELETED.getMessage(entryId));
    }
}
