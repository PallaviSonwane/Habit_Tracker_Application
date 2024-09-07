package com.dailyhabittrack.constant.enums;

public enum HabitEntryResponseMessage {

    HABIT_ENTRY_NOT_FOUND("Habit entry not found for entry id: %s"),
    HABIT_ENTRY_ALREADY_EXISTS("Habit entry already available"),
    FAILED_TO_SAVE_HABIT_ENTRY("Failed to save habit entry for entry id: %s"),
    HABIT_ENTRY_SUCCESSFULLY_SAVED("Habit entry with entry id %s save successfully"),
    HABIT_NOT_FOUND("Habit not found for id: %s");
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
