package com.dailyhabittrack.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HabitEntryResponse {
    private Long entryId;
    private Long habitId;
    private Integer value;
    private LocalDateTime timestamp;
}
