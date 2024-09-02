package com.dailyhabittrack.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dailyhabittrack.constant.enums.HabitEntryResponseMessage;
import com.dailyhabittrack.entity.HabitEntry;
import com.dailyhabittrack.exception.HabitException;
import com.dailyhabittrack.mapper.HabitEntryMapper;
import com.dailyhabittrack.repository.HabitEntryRepository;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;
import com.dailyhabittrack.service.HabitEntryService;

@Service
public class HabitEntryServiceImpl implements HabitEntryService {
    private static final Logger logger = LoggerFactory.getLogger(HabitEntryServiceImpl.class);

    private final HabitEntryRepository habitEntryRepository;

    public HabitEntryServiceImpl(HabitEntryRepository habitEntryRepository) {
        this.habitEntryRepository = habitEntryRepository;
    }

    @Override
    public HabitEntryResponse createHabitEntry(HabitEntryRequest habitEntryRequest) {
        String workFlow = "HabitEntryServiceImpl.createHabitEntry";
        try {
            HabitEntry habitEntry = HabitEntryMapper.INSTANCE.habitEntryRequestToHabitEntry(habitEntryRequest);

            // Use the provided timestamp if available, otherwise set to current time
            habitEntry.setTimestamp(habitEntryRequest.getTimestamp() != null ? 
                                    habitEntryRequest.getTimestamp() : LocalDateTime.now());

            HabitEntry createdHabitEntry = habitEntryRepository.save(habitEntry);
            return HabitEntryMapper.INSTANCE.habitEntryToHabitEntryResponse(createdHabitEntry);
        } catch (Exception ex) {
            throw new HabitException(
                    HabitEntryResponseMessage.FAILED_TO_SAVE_HABIT_ENTRY.getMessage(habitEntryRequest.getHabitId()),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, workFlow);
        }
    }

    @Override
    public List<HabitEntryResponse> getAllHabitEntries() {
        String workFlow = "HabitEntryServiceImpl.getAllHabitEntries";
        List<HabitEntry> habitEntryList = habitEntryRepository.findAll();
        if (habitEntryList.isEmpty()) {
            throw new HabitException(
                    HabitEntryResponseMessage.HABIT_ENTRY_NOT_EXISTS.getMessage(),
                    HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT, workFlow);
        }
        return HabitEntryMapper.INSTANCE.habitEntryListToHabitEntryResponseList(habitEntryList);
    }

    @Override
    public HabitEntryResponse getHabitEntry(Long entryId) {
        String workFlow = "HabitEntryServiceImpl.getHabitEntry";
        HabitEntry habitEntry = habitEntryRepository.findById(entryId)
                .orElseThrow(() -> new HabitException(
                        HabitEntryResponseMessage.HABIT_ENTRY_NOT_FOUND.getMessage(entryId),
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow));
        return HabitEntryMapper.INSTANCE.habitEntryToHabitEntryResponse(habitEntry);
    }

    @Override
    public void updateHabitEntry(HabitEntryRequest updatedHabitEntryRequest, Long entryId) {
        String workFlow = "HabitEntryServiceImpl.updateHabitEntry";
        HabitEntry existingHabitEntry = habitEntryRepository.findById(entryId)
                .orElseThrow(() -> new HabitException(
                        HabitEntryResponseMessage.HABIT_ENTRY_NOT_FOUND.getMessage(entryId),
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow));

        HabitEntry updateHabitEntry = HabitEntryMapper.INSTANCE.habitEntryRequestToHabitEntry(updatedHabitEntryRequest);
        updateHabitEntry.setEntryId(existingHabitEntry.getEntryId());

        // Use provided timestamp or retain existing one
        updateHabitEntry.setTimestamp(updatedHabitEntryRequest.getTimestamp() != null ? 
                                      updatedHabitEntryRequest.getTimestamp() : existingHabitEntry.getTimestamp());

        habitEntryRepository.save(updateHabitEntry);
        logger.info("Updated habit entry details: {}", updateHabitEntry);
    }

    @Override
    @Transactional
    public void deleteHabitEntry(Long entryId) {
        String workFlow = "HabitEntryServiceImpl.deleteHabitEntry";
        HabitEntry existingHabitEntry = habitEntryRepository.findById(entryId)
                .orElseThrow(() -> new HabitException(
                        HabitEntryResponseMessage.HABIT_ENTRY_NOT_FOUND.getMessage(entryId),
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow));
        habitEntryRepository.delete(existingHabitEntry);
    }
}
