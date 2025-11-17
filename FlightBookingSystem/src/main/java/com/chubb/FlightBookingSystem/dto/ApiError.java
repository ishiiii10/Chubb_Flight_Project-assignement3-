package com.chubb.FlightBookingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Simple structure for sending error details back to the client.
 */
@Getter
@AllArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;
    private String errorCode;
    private String message;
    private String path;
}