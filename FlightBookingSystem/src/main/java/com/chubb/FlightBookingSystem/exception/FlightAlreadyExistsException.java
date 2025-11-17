package com.chubb.FlightBookingSystem.exception;

public class FlightAlreadyExistsException extends RuntimeException {

    public FlightAlreadyExistsException(String code) {
        super("Flight with code '" + code + "' already exists");
    }
}