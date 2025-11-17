package com.chubb.FlightBookingSystem.exception;

public class AirlineAlreadyExistsException extends RuntimeException {

    public AirlineAlreadyExistsException(String code) {
        super("Airline with code '" + code + "' already exists");
    }
}
