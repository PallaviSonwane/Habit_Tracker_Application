package com.dailyhabittrack.service;

import java.util.List;

import com.dailyhabittrack.entity.HabitsHabitEntryJoin;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;

public interface HabitEntryService {

    HabitEntryResponse createHabitEntry(HabitEntryRequest request);

    HabitEntryResponse getHabitEntryById(Long entryId);

    //List<HabitEntryResponse> getEntriesByHabitId(Long habitId);

    void deleteHabitEntry(Long entryId);

    void updateHabitValue(Long habitId, Integer value);

    List<HabitsHabitEntryJoin> getHabitEntriesByHabitId(Long habitId);
}
