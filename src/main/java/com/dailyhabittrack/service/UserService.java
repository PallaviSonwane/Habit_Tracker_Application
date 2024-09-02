package com.dailyhabittrack.service;

import java.util.List;

import com.dailyhabittrack.request.UserRequest;
import com.dailyhabittrack.response.UserResponse;

public interface UserService {

    UserResponse createUserAccount(UserRequest userRequest);

    List<UserResponse> getAllCustomer();

    UserResponse getCustomer(Long userId);

    void updateCustomer(UserRequest user, Long userId);

    void deleteCustomer(Long userId);
}
