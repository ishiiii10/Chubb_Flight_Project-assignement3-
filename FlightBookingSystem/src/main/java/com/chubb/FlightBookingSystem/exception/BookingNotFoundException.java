package com.chubb.FlightBookingSystem.exception;

public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(String bookingRef) {
        super("Booking with reference '" + bookingRef + "' not found");
    }
}