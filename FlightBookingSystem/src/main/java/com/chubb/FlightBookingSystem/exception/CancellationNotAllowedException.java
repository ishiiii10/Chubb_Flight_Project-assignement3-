package com.chubb.FlightBookingSystem.exception;

public class CancellationNotAllowedException extends RuntimeException {

    public CancellationNotAllowedException(String pnr) {
        super("Ticket with PNR '" + pnr + "' can only be cancelled before 24 hours of departure");
    }
}