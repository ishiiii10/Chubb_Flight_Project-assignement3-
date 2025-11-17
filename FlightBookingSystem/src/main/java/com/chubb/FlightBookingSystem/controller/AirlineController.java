package com.chubb.FlightBookingSystem.controller;

import com.chubb.FlightBookingSystem.entity.AirlineDetails;
import com.chubb.FlightBookingSystem.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airlines")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    // Add a new airline
    @PostMapping
    public ResponseEntity<AirlineDetails> addAirline(@RequestBody AirlineDetails airlineDetails) {
        AirlineDetails savedAirline = airlineService.addAirline(airlineDetails);
        return new ResponseEntity<>(savedAirline, HttpStatus.CREATED);
    }

    // List all airlines
    @GetMapping
    public ResponseEntity<List<AirlineDetails>> getAllAirlines() {
        List<AirlineDetails> airlines = airlineService.getAllAirlines();
        return ResponseEntity.ok(airlines);
    }
}