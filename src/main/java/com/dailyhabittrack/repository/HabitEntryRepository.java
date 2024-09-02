package com.dailyhabittrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dailyhabittrack.entity.HabitEntry;

@Repository
public interface HabitEntryRepository extends JpaRepository<HabitEntry, Long> {
}
