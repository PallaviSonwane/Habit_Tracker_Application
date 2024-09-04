package com.dailyhabittrack.constant.enums;

public enum UnitResponseMessage {

    UNIT_NOT_FOUND("Unit not found for id : %s"),
    UNIT_NOT_EXISTS("Unit not available"),
    FAILED_TO_SAVE_UNIT("Failed to save Unit for id : %s"),
    UNIT_SUCCESSFULLY_DELETED("Unit with id %s deleted successfully"),
    UNIT_SUCCESSFULLY_UPDATED("Unit with id %s updated successfully"),
    UNIT_ALREADY_DELETED("Unit with id %s already deleted");

    private final String message;

    UnitResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(Long unitId) {
        return String.format(message, unitId);
    }
}
