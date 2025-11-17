package com.chubb.FlightBookingSystem.exception;

public class AirlineNotFoundException extends RuntimeException {

    public AirlineNotFoundException(Long id) {
        super("Airline with id '" + id + "' not found");
    }
}