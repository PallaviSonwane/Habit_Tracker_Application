package com.dailyhabittrack.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dailyhabittrack.constant.enums.UserResponseMessage;
import com.dailyhabittrack.request.UserRequest;
import com.dailyhabittrack.response.UserResponse;
import com.dailyhabittrack.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUserAccount(@Valid @RequestBody UserRequest userRequest) {
        logger.info("User create request received {}:", userRequest);
        UserResponse creationResponse = userService.createUserAccount(userRequest);
        logger.info("User created {}", creationResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(creationResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> userList = userService.getAllCustomer();
        logger.info("user Information Fetched for all customers : {}", userList);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> getUserData(@PathVariable("user-id") Long userId) {
        logger.info("Fetching user data information request received for user id : {}", userId);
        UserResponse userData = userService.getCustomer(userId);
        logger.info("User Information Fetched For user id  : {}", userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(userData);
    }

    @PutMapping("/{user-id}")
    public ResponseEntity<String> updateUserData(@Valid @PathVariable("user-id") Long userId,
                                                        @RequestBody UserRequest updateduser) {
        logger.info("user update request received for user id {} with user details : {}",
                userId, updateduser);
        userService.updateCustomer(updateduser, userId);
        logger.info("User is updated for user id : {}", userId);
        return ResponseEntity.ok()
                .body(UserResponseMessage.USER_SUCCESSFULLY_UPDATED.getMessage(userId));
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-id") Long userId) {
        logger.info("User delete Request received for user id : {}", userId);
        userService.deleteCustomer(userId);
        logger.info("User is deleted for user id : {}", userId);
        return ResponseEntity.ok()
                .body(UserResponseMessage.USER_SUCCESSFULLY_DELETED.getMessage(userId));
    }
}
