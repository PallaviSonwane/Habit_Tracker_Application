package com.dailyhabittrack.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dailyhabittrack.entity.HabitEntry;
import com.dailyhabittrack.entity.HabitsHabitEntryJoin;

@Repository
public interface HabitEntryRepository extends JpaRepository<HabitEntry, Long> {

    boolean existsByHabitIdAndDate(Long habitId, LocalDate date);

    @Query("SELECT new com.dailyhabittrack.entity.HabitsHabitEntryJoin(" +
            "h.habitId, h.habitName, h.goal, h.unitName, h.frequency, he.date, he.value) " +
            "FROM Habit h JOIN HabitEntry he ON h.habitId = he.habitId " +
            "WHERE h.habitId = :habitId")
    List<HabitsHabitEntryJoin> findHabitEntriesByHabitId(Long habitId);

    @Query("SELECT he FROM HabitEntry he WHERE he.habitId = :habitId")
    HabitEntry findByHabitId(Long habitId);

    HabitEntry findByHabitIdAndDate(Long habitId, LocalDate date);

    @Query("SELECT e FROM HabitEntry e WHERE e.date BETWEEN :startDate AND :endDate")
    List<HabitEntry> findEntriesByDateRange(LocalDate startDate, LocalDate endDate);

    List<HabitEntry> findByDate(LocalDate date);
}
