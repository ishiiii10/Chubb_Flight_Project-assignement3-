package com.chubb.FlightBookingSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FlightRequest {

    @NotNull(message = "Airline id is required")
    @Positive
    private Long airlineId;

    @NotBlank(message = "Flight code is required")
    @Size(max = 10)
    private String flightCode;

    @NotBlank(message = "Source airport is required")
    @Size(min = 3, max = 3, message = "From airport must be 3 characters")
    private String fromAirport;

    @NotBlank(message = "Destination airport is required")
    @Size(min = 3, max = 3, message = "To airport must be 3 characters")
    private String toAirport;

    @NotNull(message = "Departure time is required")
    @FutureOrPresent(message = "Departure time cannot be in the past")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Future(message = "Arrival time must be in the future")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalSeats;

    @NotNull(message = "Base fare is required")
    @Positive(message = "Base fare must be positive")
    private Double baseFare;
}