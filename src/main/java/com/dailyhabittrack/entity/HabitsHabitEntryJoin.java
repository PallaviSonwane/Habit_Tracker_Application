package com.dailyhabittrack.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HabitsHabitEntryJoin {

    private Long habitId;
    private String habitName;
    private Integer goal;
    private String unitName;
    private String frequency;
    private Integer value;
    private LocalDate date;

    public HabitsHabitEntryJoin(Long habitId, String habitName, Integer goal, String unitName,
            String frequency, LocalDate date, Integer value) {
        this.habitId = habitId;
        this.habitName = habitName;
        this.goal = goal;
        this.unitName = unitName;
        this.frequency = frequency;
        this.date = date;
        this.value = value;
    }
}
