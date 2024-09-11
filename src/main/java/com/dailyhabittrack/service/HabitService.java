package com.dailyhabittrack.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.dailyhabittrack.request.HabitRequest;
import com.dailyhabittrack.response.HabitResponse;

public interface HabitService {

    HabitResponse createHabit(HabitRequest habitRequest);

    List<HabitResponse> getAllHabits();

    HabitResponse getHabit(Long habitId);

    void updateHabit(HabitRequest updatedHabit, Long habitId);
    
    void deleteHabit(Long habitId);

    List<HabitResponse> getHabitsByDate(LocalDate date);

    Map<String, Map<String, Integer>> getMonthlyProgressForAllHabits(LocalDate startDate, LocalDate endDate);

}
