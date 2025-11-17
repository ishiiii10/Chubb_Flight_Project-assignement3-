package com.chubb.FlightBookingSystem.exception;

public class BookingAlreadyCancelledException extends RuntimeException {

    public BookingAlreadyCancelledException(String bookingRef) {
        super("Booking with reference '" + bookingRef + "' is already cancelled");
    }
}