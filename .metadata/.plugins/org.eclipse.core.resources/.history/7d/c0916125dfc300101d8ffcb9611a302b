package com.chubb.FlightBookingSystem.controller;

import com.chubb.FlightBookingSystem.dto.FlightRequest;
import com.chubb.FlightBookingSystem.entity.FlightInfo;
import com.chubb.FlightBookingSystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightInfo> createFlight(@RequestBody FlightRequest request) {
        FlightInfo savedFlight = flightService.createFlight(request);
        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FlightInfo>> getAllFlights() {
        List<FlightInfo> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }
}