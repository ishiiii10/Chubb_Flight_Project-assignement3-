package com.chubb.FlightBookingSystem.exception;

public class TravelerNotFoundException extends RuntimeException {

    public TravelerNotFoundException(String email) {
        super("Traveler with email '" + email + "' not found");
    }
}