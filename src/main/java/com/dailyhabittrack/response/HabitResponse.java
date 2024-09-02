package com.dailyhabittrack.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HabitResponse {
    
    private Long habitId;
    private String habitName;
    private Integer goal;
    private String unitName;
    private String frequency;
    private LocalDate date; 
}
