package com.dailyhabittrack.request;
    
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HabitEntryRequest {
    private Long habitId;
    private Integer value;
    private LocalDate date;
}
