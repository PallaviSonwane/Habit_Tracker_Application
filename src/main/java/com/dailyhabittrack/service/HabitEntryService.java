package com.dailyhabittrack.service;

import java.util.List;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;

public interface HabitEntryService {

    HabitEntryResponse createHabitEntry(HabitEntryRequest habitEntryRequest);

    List<HabitEntryResponse> getAllHabitEntries();

    HabitEntryResponse getHabitEntry(Long entryId);

    void updateHabitEntry(HabitEntryRequest updatedHabitEntry, Long entryId);

    void deleteHabitEntry(Long entryId);
}
