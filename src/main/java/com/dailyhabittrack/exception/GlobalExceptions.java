package com.dailyhabittrack.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dailyhabittrack.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getStatusCode(), exception.getExceptionMessage(),
                exception.getStatus());
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(HabitEntryException.class)
    public ResponseEntity<ErrorResponse> handleHabitEntryException(HabitEntryException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getStatusCode(), exception.getExceptionMessage(),
                exception.getStatus());
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @ExceptionHandler(HabitException.class)
    public ResponseEntity<ErrorResponse> handleHabitException(HabitException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getStatusCode(),
                exception.getExceptionMessage(),
                exception.getStatus()
        );
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }
    

    @ExceptionHandler(UnitException.class)
    public ResponseEntity<ErrorResponse> handleUnitException(UnitException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getStatusCode(), exception.getExceptionMessage(),
                exception.getStatus());
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @ExceptionHandler(HabitEntryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleHabitEntryAlreadyExistsException(HabitEntryAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.CONFLICT.value(), 
            ex.getMessage(),             
            HttpStatus.CONFLICT         
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
}
