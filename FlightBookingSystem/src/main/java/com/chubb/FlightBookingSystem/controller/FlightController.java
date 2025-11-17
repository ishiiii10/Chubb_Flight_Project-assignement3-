package com.chubb.FlightBookingSystem.controller;

import com.chubb.FlightBookingSystem.dto.FlightRequest;

import com.chubb.FlightBookingSystem.entity.FlightInfo;
import com.chubb.FlightBookingSystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chubb.FlightBookingSystem.dto.FlightSearchRequest;


import java.time.LocalDate;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightInfo> createFlight( @Valid @RequestBody FlightRequest request) {
        FlightInfo savedFlight = flightService.createFlight(request);
        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FlightInfo>> getAllFlights() {
        List<FlightInfo> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }
    
    @GetMapping("/search")
    public ResponseEntity<java.util.List<FlightInfo>> searchFlights(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("date") String dateString,
            @RequestParam("passengers") int passengers) {

        FlightSearchRequest search = new FlightSearchRequest();
        search.setFromAirport(from);
        search.setToAirport(to);
        search.setPassengers(passengers);
        search.setTravelDate(LocalDate.parse(dateString)); // expects YYYY-MM-DD

        java.util.List<FlightInfo> results = flightService.searchFlights(search);
        return ResponseEntity.ok(results);
    }
}