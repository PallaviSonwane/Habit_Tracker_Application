package com.dailyhabittrack.entity;

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

}
