package com.dailyhabittrack.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class HabitEntryResponse {
    private Long entryId;
    private Long habitId;
    private Integer value;
    private LocalDate date;
}

