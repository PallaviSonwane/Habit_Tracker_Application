package com.dailyhabittrack.service.impl;

import com.dailyhabittrack.constant.enums.HabitResponseMessage;
import com.dailyhabittrack.entity.HabitEntry;
import com.dailyhabittrack.entity.HabitsHabitEntryJoin;
import com.dailyhabittrack.exception.HabitException;
import com.dailyhabittrack.mapper.HabitEntryMapper;
import com.dailyhabittrack.repository.HabitEntryRepository;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;
import com.dailyhabittrack.service.HabitEntryService;

import jakarta.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HabitEntryServiceImpl implements HabitEntryService {

    private static final Logger logger = LoggerFactory.getLogger(HabitEntryServiceImpl.class);

    private final HabitEntryRepository habitEntryRepository;

    public HabitEntryServiceImpl(HabitEntryRepository habitEntryRepository) {
        this.habitEntryRepository = habitEntryRepository;
    }

    @Override
    public HabitEntryResponse createHabitEntry(HabitEntryRequest request) {
        try {
            HabitEntry habitEntry = HabitEntryMapper.INSTANCE.habitEntryRequestToEntity(request);
            boolean entryExists = habitEntryRepository.existsByHabitIdAndDate(habitEntry.getHabitId(),
                    habitEntry.getDate());

            if (entryExists) {
                // If entry exists, return a success response without saving
                return HabitEntryMapper.INSTANCE.habitEntryToResponse(habitEntry);
            }

            // If entry does not exist, save the new entry
            HabitEntry savedHabitEntry = habitEntryRepository.save(habitEntry);
            return HabitEntryMapper.INSTANCE.habitEntryToResponse(savedHabitEntry);

        } catch (Exception ex) {
            logger.error("Failed to save Habit Entry: {}", request, ex);
            throw new HabitException(
                    "Failed to save habit entry.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "HabitEntryServiceImpl.createHabitEntry");
        }
    }

    @Override
    public HabitEntryResponse getHabitEntryById(Long entryId) {
        Optional<HabitEntry> optionalHabitEntry = habitEntryRepository.findById(entryId);
        if (!optionalHabitEntry.isPresent()) {
            throw new HabitException(
                    HabitResponseMessage.HABIT_NOT_FOUND.getMessage(entryId),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "HabitEntryServiceImpl.getHabitEntryById");
        }
        return HabitEntryMapper.INSTANCE.habitEntryToResponse(optionalHabitEntry.get());
    }

    // @Override
    // public List<HabitEntryResponse> getEntriesByHabitId(Long habitId) {
    //     List<HabitEntry> entries = habitEntryRepository.findByHabitId(habitId);
    //     if (entries.isEmpty()) {
    //         throw new HabitException(
    //                 HabitResponseMessage.HABIT_NOT_EXISTS.getMessage(),
    //                 HttpStatus.NO_CONTENT.value(),
    //                 HttpStatus.NO_CONTENT,
    //                 "HabitEntryServiceImpl.getEntriesByHabitId");
    //     }
    //     return HabitEntryMapper.INSTANCE.habitEntryListToResponseList(entries);
    // }

    @Override
    @Transactional
    public void deleteHabitEntry(Long entryId) {
        Optional<HabitEntry> optionalHabitEntry = habitEntryRepository.findById(entryId);
        if (!optionalHabitEntry.isPresent()) {
            throw new HabitException(
                    HabitResponseMessage.HABIT_NOT_FOUND.getMessage(entryId),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "HabitEntryServiceImpl.deleteHabitEntry");
        }
        habitEntryRepository.deleteById(entryId);
    }

    @Override
    public void updateHabitValue(Long habitId, Integer value) {
        HabitEntry habitEntry = habitEntryRepository.findByHabitId(habitId);

        if (habitEntry != null) {
            habitEntry.setValue(value);

            habitEntryRepository.save(habitEntry);
        } else {
            throw new EntityNotFoundException("Habit entry not found with ID: " + habitId);
        }
    }

    public List<HabitsHabitEntryJoin> getHabitEntriesByHabitId(Long habitId) {
        return habitEntryRepository.findHabitEntriesByHabitId(habitId);
    }
}
