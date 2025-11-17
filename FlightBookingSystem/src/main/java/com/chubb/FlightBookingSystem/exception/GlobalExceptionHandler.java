package com.chubb.FlightBookingSystem.exception;

import com.chubb.FlightBookingSystem.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> buildErrorResponse(
            HttpStatus status, String code, String message, HttpServletRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                code,
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(AirlineAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAirlineAlreadyExists(
            AirlineAlreadyExistsException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, "AIRLINE_EXISTS", ex.getMessage(), request);
    }

    @ExceptionHandler(AirlineNotFoundException.class)
    public ResponseEntity<ApiError> handleAirlineNotFound(
            AirlineNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "AIRLINE_NOT_FOUND", ex.getMessage(), request);
    }

    @ExceptionHandler(FlightAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleFlightAlreadyExists(
            FlightAlreadyExistsException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, "FLIGHT_EXISTS", ex.getMessage(), request);
    }
    
    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<ApiError> handleFlightNotFound(
            FlightNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "FLIGHT_NOT_FOUND", ex.getMessage(), request);
    }

    @ExceptionHandler(NotEnoughSeatsException.class)
    public ResponseEntity<ApiError> handleNotEnoughSeats(
            NotEnoughSeatsException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "NOT_ENOUGH_SEATS", ex.getMessage(), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", ex.getMessage(), request);
    }

    // Fallback for all other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", ex.getMessage(), request);
    }
}