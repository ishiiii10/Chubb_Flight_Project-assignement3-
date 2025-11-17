package com.chubb.FlightBookingSystem.exception;

public class NotEnoughSeatsException extends RuntimeException {

    public NotEnoughSeatsException(int requested, int available) {
        super("Not enough seats. Requested: " + requested + ", Available: " + available);
    }
}