package com.dailyhabittrack.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Long userId;
    private String userName;
    private String email;
    private String password;
    private LocalDateTime timestamp;
    private boolean deleted;
}
