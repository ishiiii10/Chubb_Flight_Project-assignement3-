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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
    
    public Object searchFlights(FlightSearchRequest search) {

        // Basic validations
        if (search.getFromAirport() == null || search.getToAirport() == null) {
            throw new IllegalArgumentException("From and To airports are required");
        }

        String from = search.getFromAirport().trim().toUpperCase();
        String to = search.getToAirport().trim().toUpperCase();

        if (from.equals(to)) {
            throw new IllegalArgumentException("Source and destination airports cannot be same");
        }

        if (search.getTravelDate() == null) {
            throw new IllegalArgumentException("Travel date is required");
        }

        if (search.getTravelDate().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Travel date cannot be in the past");
        }

        if (search.getPassengers() <= 0) {
            throw new IllegalArgumentException("Passenger count must be greater than zero");
        }
        if (search.getPassengers() > 9) {
            throw new IllegalArgumentException("Passenger count cannot be more than 9 in one booking");
        }

        // one way default
        String tripType = search.getTripType() == null ?
                "ONE_WAY" : search.getTripType().trim().toUpperCase();

        if (!tripType.equals("ONE_WAY") && !tripType.equals("ROUND_TRIP")) {
            throw new IllegalArgumentException("Trip type must be ONE_WAY or ROUND_TRIP");
        }

        // Search logic
        java.util.List<FlightInfo> onwardFlights = flightRepo
                .findByFromAirportAndToAirportAndDepartureTimeBetween(
                        from,
                        to,
                        search.getTravelDate().atStartOfDay(),
                        search.getTravelDate().atTime(java.time.LocalTime.MAX)
                );

        // Remove flights without enough seats
        onwardFlights.removeIf(f -> f.getAvailableSeats() < search.getPassengers());

        // If simple one-way, return list
        if (tripType.equals("ONE_WAY")) {
            return onwardFlights;
        }

        // ===== ROUND TRIP LOGIC =====
        if (search.getReturnDate() == null) {
            throw new IllegalArgumentException("Return date is required for round trip");
        }

        if (search.getReturnDate().isBefore(search.getTravelDate())) {
            throw new IllegalArgumentException("Return date cannot be before travel date");
        }

        java.util.List<FlightInfo> returnFlights = flightRepo
                .findByFromAirportAndToAirportAndDepartureTimeBetween(
                        to,
                        from,
                        search.getReturnDate().atStartOfDay(),
                        search.getReturnDate().atTime(java.time.LocalTime.MAX)
                );

        returnFlights.removeIf(f -> f.getAvailableSeats() < search.getPassengers());

        // Build round-trip result object
        Map<String, Object> result = new HashMap<>();
        result.put("tripType", "ROUND_TRIP");
        result.put("onwardFlights", onwardFlights);
        result.put("returnFlights", returnFlights);

        return result;
    }
}