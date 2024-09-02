package com.dailyhabittrack.constant.enums;

public enum UserResponseMessage {

    USER_NOT_FOUND("User not found for user id : %s"),
    USER_NOT_EXISTS("User not available"),
    FAILED_TO_SAVE_USER("Failed to save user for user id : %s"),
    USER_SUCCESSFULLY_DELETED("User with user id  %s deleted successfully"),
    USER_SUCCESSFULLY_UPDATED("User with user id  %s updated successfully"),
    USER_ALREADY_DELETED("User with user id %s already deleted");

    private final String message;

    UserResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Long userId) {
        return String.format(message, userId);
    }
}
