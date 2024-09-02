package com.dailyhabittrack.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitEntryRequest {
    private Long habitId;
    private Integer value;
    private LocalDateTime timestamp; // New field to accept timestamp from request
}
