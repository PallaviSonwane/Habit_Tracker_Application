package com.dailyhabittrack.service.impl;

import com.dailyhabittrack.constant.enums.HabitEntryResponseMessage;
import com.dailyhabittrack.entity.HabitEntry;
import com.dailyhabittrack.entity.HabitsHabitEntryJoin;
import com.dailyhabittrack.exception.HabitException;
import com.dailyhabittrack.mapper.HabitEntryMapper;
import com.dailyhabittrack.repository.HabitEntryRepository;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;
import com.dailyhabittrack.service.HabitEntryService;

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
            boolean entryExists = habitEntryRepository.existsByHabitIdAndDate(habitEntry.getHabitId(), habitEntry.getDate());

            if (entryExists) {
                logger.info("Habit entry already exist");
                return HabitEntryMapper.INSTANCE.habitEntryToResponse(habitEntry);
            }

            HabitEntry savedHabitEntry = habitEntryRepository.save(habitEntry);
            logger.info("Habit entry successfully save");
            return HabitEntryMapper.INSTANCE.habitEntryToResponse(savedHabitEntry);

        } catch (Exception ex) {
            logger.error(HabitEntryResponseMessage.FAILED_TO_SAVE_HABIT_ENTRY.getMessage(request.getHabitId()), ex);
            throw new HabitException(
                    HabitEntryResponseMessage.FAILED_TO_SAVE_HABIT_ENTRY.getMessage(request.getHabitId()),
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
                    HabitEntryResponseMessage.HABIT_ENTRY_NOT_FOUND.getMessage(entryId),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "HabitEntryServiceImpl.getHabitEntryById");
        }
        return HabitEntryMapper.INSTANCE.habitEntryToResponse(optionalHabitEntry.get());
    }

    @Override
    @Transactional
    public void deleteHabitEntry(Long entryId) {
        Optional<HabitEntry> optionalHabitEntry = habitEntryRepository.findById(entryId);
        if (!optionalHabitEntry.isPresent()) {
            throw new HabitException(
                    HabitEntryResponseMessage.HABIT_ENTRY_NOT_FOUND.getMessage(entryId),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "HabitEntryServiceImpl.deleteHabitEntry");
        }
        habitEntryRepository.deleteById(entryId);
        logger.info("Habit entry deleted successfully");
    }

    @Override
    public HabitEntryResponse updateHabitValue(HabitEntryRequest updEntryRequest) {
        HabitEntry habitEntry = HabitEntryMapper.INSTANCE.habitEntryRequestToEntity(updEntryRequest);
        HabitEntry entryExists = habitEntryRepository.findByHabitIdAndDate(habitEntry.getHabitId(), habitEntry.getDate());
        if (entryExists!=null) {
            habitEntry.setEntryId(entryExists.getEntryId());
            habitEntry.setDate(entryExists.getDate());
            habitEntry.setHabitId(entryExists.getHabitId());
            HabitEntry updatedHabitEntry = habitEntryRepository.save(habitEntry);
            logger.info("Habit entry successfully updated");
            return HabitEntryMapper.INSTANCE.habitEntryToResponse(updatedHabitEntry);
        } else {
            throw new HabitException(
                    HabitEntryResponseMessage.HABIT_ENTRY_NOT_FOUND.getMessage(habitEntry.getHabitId()),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "HabitEntryServiceImpl.updateHabitValue");
        }
    }

    @Override
    public List<HabitsHabitEntryJoin> getHabitEntriesByHabitId(Long habitId) {
        return habitEntryRepository.findHabitEntriesByHabitId(habitId);
    }
}
