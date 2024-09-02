package com.dailyhabittrack.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dailyhabittrack.entity.Habit;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    Habit findByHabitId(Long habitId);

    void deleteByHabitId(Long habitId);

    @Query("SELECT h FROM Habit h WHERE h.date <= :date")
    List<Habit> findByDateAfter(@Param("date") LocalDate date);

    Habit findByHabitName(String habitName);

}
