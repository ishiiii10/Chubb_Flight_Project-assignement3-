package com.chubb.FlightBookingSystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Request body for creating a flight.
 */
@Getter
@Setter
public class FlightRequest {

    private Long airlineId;
    private String flightCode;
    private String fromAirport;
    private String toAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer totalSeats;
    private Double baseFare;
}