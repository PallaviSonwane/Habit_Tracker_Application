package com.dailyhabittrack.request;

import lombok.Getter;

@Getter
public class UserRequest {

    private String userName;
    private String email;
    private String password;
    private boolean deleted;
}
