package com.dailyhabittrack.service;

import java.time.LocalDate;
import java.util.List;

import com.dailyhabittrack.entity.HabitsHabitEntryJoin;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;

public interface HabitEntryService {

    HabitEntryResponse createHabitEntry(HabitEntryRequest request);

    HabitEntryResponse getHabitEntryById(Long entryId);

    void deleteHabitEntry(Long entryId);

    HabitEntryResponse updateHabitValue(HabitEntryRequest upEntryRequest);

    List<HabitsHabitEntryJoin> getHabitEntriesByHabitId(Long habitId);

    List<HabitsHabitEntryJoin> getHabitEntriesByDate(LocalDate date);
}
