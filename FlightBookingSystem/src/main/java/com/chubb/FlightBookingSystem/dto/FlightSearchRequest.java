package com.chubb.FlightBookingSystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FlightSearchRequest {

    private String fromAirport;
    private String toAirport;
    private LocalDate travelDate;
    private int passengers;
    
    private String tripType;        // "ONE_WAY" or "ROUND_TRIP"
    private LocalDate returnDate;   // optional for round trip
}