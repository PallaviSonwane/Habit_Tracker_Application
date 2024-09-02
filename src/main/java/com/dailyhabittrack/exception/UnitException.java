package com.dailyhabittrack.exception;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnitException extends RuntimeException {

    private final String exceptionMessage;
    private final Integer statusCode;
    private final HttpStatusCode status;
    private final String workFlow;

    public UnitException(String exceptionMessage, Integer statusCode, HttpStatusCode status, String workFlow) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
        this.statusCode = statusCode;
        this.status = status;
        this.workFlow = workFlow;
    }
}
