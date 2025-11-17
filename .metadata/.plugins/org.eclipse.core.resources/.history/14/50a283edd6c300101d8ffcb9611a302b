package com.chubb.FlightBookingSystem.service;

import com.chubb.FlightBookingSystem.dto.FlightRequest;
import com.chubb.FlightBookingSystem.entity.AirlineDetails;
import com.chubb.FlightBookingSystem.entity.FlightInfo;
import com.chubb.FlightBookingSystem.exception.AirlineNotFoundException;
import com.chubb.FlightBookingSystem.repository.AirlineRepo;
import com.chubb.FlightBookingSystem.repository.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepo flightRepo;

    @Autowired
    private AirlineRepo airlineRepo;

    // Create a new flight from request DTO
    public FlightInfo createFlight(FlightRequest request) {

        AirlineDetails airline = airlineRepo.findById(request.getAirlineId())
                .orElseThrow(() -> new AirlineNotFoundException(request.getAirlineId()));

        FlightInfo flight = FlightInfo.builder()
                .flightCode(request.getFlightCode())
                .airline(airline)
                .fromAirport(request.getFromAirport())
                .toAirport(request.getToAirport())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats()) // initially all seats free
                .baseFare(request.getBaseFare())
                .status("SCHEDULED")
                .build();

        return flightRepo.save(flight);
    }

    public List<FlightInfo> getAllFlights() {
        return flightRepo.findAll();
    }
}