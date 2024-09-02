package com.dailyhabittrack.constant.enums;

public enum HabitResponseMessage {

    HABIT_NOT_FOUND("Habit not found for habit id : %s"),
    HABIT_ALREADY_ADDED("Habit already added"),
    HABIT_NOT_EXISTS("Habit not available"),
    FAILED_TO_SAVE_HABIT("Failed to save habit "),
    HABIT_SUCCESSFULLY_DELETED("Habit with habit id %s deleted successfully"),
    HABIT_SUCCESSFULLY_UPDATED("Habit with habit id %s updated successfully"),
    HABIT_ALREADY_DELETED("Habit with habit id %s already deleted");

    private final String message;

    HabitResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Long habitId) {
        return String.format(message, habitId);
    }
}
