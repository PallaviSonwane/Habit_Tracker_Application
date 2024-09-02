package com.dailyhabittrack.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitRequest {

    private String habitName;
    private Integer goal;
    private String unitName;
    private String frequency;
    private LocalDate date; 

}
