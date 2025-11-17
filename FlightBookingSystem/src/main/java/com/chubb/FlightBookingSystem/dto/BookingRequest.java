package com.chubb.FlightBookingSystem.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data I need from the client to create a booking.
 */
@Getter
@Setter
public class BookingRequest {

    // traveler details
    private String fullName;
    private String email;
    private String mobileNumber;
    private String country;
    private String idDocumentNumber;

    // booking details
    private Long flightId;
    private Integer seatsToBook;
}