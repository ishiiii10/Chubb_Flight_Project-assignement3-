package com.chubb.FlightBookingSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {

    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "Flight Booking System");
        response.put("status", "ACTIVE");
        response.put("checkedAt", LocalDateTime.now());
        response.put("mode", "Development");

        return ResponseEntity.ok(response);
    }
}
