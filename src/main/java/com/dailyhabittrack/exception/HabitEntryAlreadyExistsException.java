package com.dailyhabittrack.exception;


public class HabitEntryAlreadyExistsException extends RuntimeException {
    public HabitEntryAlreadyExistsException(String message) {
        super(message);
    }
}
