package com.chubb.FlightBookingSystem.service;

import com.chubb.FlightBookingSystem.dto.FlightRequest;
import com.chubb.FlightBookingSystem.entity.AirlineDetails;
import com.chubb.FlightBookingSystem.entity.FlightInfo;
import com.chubb.FlightBookingSystem.exception.AirlineNotFoundException;
import com.chubb.FlightBookingSystem.exception.FlightAlreadyExistsException;
import com.chubb.FlightBookingSystem.repository.AirlineRepo;
import com.chubb.FlightBookingSystem.repository.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chubb.FlightBookingSystem.dto.FlightSearchRequest;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepo flightRepo;

    @Autowired
    private AirlineRepo airlineRepo;

    public FlightInfo createFlight(FlightRequest request) {

        // 1) Check duplicate flight code in a clean way
        if (flightRepo.existsByFlightCode(request.getFlightCode())) {
            throw new FlightAlreadyExistsException(request.getFlightCode());
        }

        // 2) Check airline existence
        AirlineDetails airline = airlineRepo.findById(request.getAirlineId())
                .orElseThrow(() -> new AirlineNotFoundException(request.getAirlineId()));

        // 3) Build and save entity
        FlightInfo flight = FlightInfo.builder()
                .flightCode(request.getFlightCode())
                .airline(airline)
                .fromAirport(request.getFromAirport())
                .toAirport(request.getToAirport())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats())
                .baseFare(request.getBaseFare())
                .status("SCHEDULED")
                .build();

        return flightRepo.save(flight);
    }

    public List<FlightInfo> getAllFlights() {
        return flightRepo.findAll();
    }
    
    public java.util.List<FlightInfo> searchFlights(FlightSearchRequest search) {

        // Basic validations
        if (search.getFromAirport() == null || search.getToAirport() == null) {
            throw new IllegalArgumentException("From and To airports are required");
        }

        String from = search.getFromAirport().trim().toUpperCase();
        String to = search.getToAirport().trim().toUpperCase();

        if (from.equals(to)) {
            throw new IllegalArgumentException("Source and destination airports cannot be same");
        }

        LocalDate date = search.getTravelDate();
        if (date == null) {
            throw new IllegalArgumentException("Travel date is required");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Travel date cannot be in the past");
        }

        int passengers = search.getPassengers();
        if (passengers <= 0) {
            throw new IllegalArgumentException("Passenger count must be greater than zero");
        }
        if (passengers > 9) {
            throw new IllegalArgumentException("Passenger count cannot be more than 9 in one booking");
        }

        // Build range for entire day
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        // Fetch flights
        java.util.List<FlightInfo> flights =
                flightRepo.findByFromAirportAndToAirportAndDepartureTimeBetween(
                        from, to, startOfDay, endOfDay
                );

        // Optionally, filter by available seats >= passengers
        flights.removeIf(f -> f.getAvailableSeats() < passengers);

        return flights;
    }
}