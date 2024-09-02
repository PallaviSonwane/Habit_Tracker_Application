package com.dailyhabittrack.constant.enums;

public enum HabitEntryResponseMessage {

    HABIT_ENTRY_NOT_FOUND("Habit entry not found for entry id : %s"),
    HABIT_ENTRY_NOT_EXISTS("Habit entry not available"),
    FAILED_TO_SAVE_HABIT_ENTRY("Failed to save habit entry for entry id : %s"),
    HABIT_ENTRY_SUCCESSFULLY_DELETED("Habit entry with entry id %s deleted successfully"),
    HABIT_ENTRY_SUCCESSFULLY_UPDATED("Habit entry with entry id %s updated successfully"),
    HABIT_ENTRY_ALREADY_DELETED("Habit entry with entry id %s already deleted");

    private final String message;

    HabitEntryResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Long entryId) {
        return String.format(message, entryId);
    }
}
