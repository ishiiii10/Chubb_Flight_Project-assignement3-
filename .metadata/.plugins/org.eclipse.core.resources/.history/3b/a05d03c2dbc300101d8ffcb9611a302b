package com.chubb.FlightBookingSystem.controller;

import com.chubb.FlightBookingSystem.dto.BookingRequest;
import com.chubb.FlightBookingSystem.entity.TripBooking;
import com.chubb.FlightBookingSystem.repository.BookingRepo;
import com.chubb.FlightBookingSystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepo bookingRepo;

    // Create a new booking
    @PostMapping
    public ResponseEntity<TripBooking> createBooking(@RequestBody BookingRequest request) {
        TripBooking booking = bookingService.createBooking(request);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    // Get booking by booking reference
    @GetMapping("/{bookingRef}")
    public ResponseEntity<TripBooking> getBooking(@PathVariable String bookingRef) {
        Optional<TripBooking> bookingOpt = bookingRepo.findByBookingRef(bookingRef);
        return bookingOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
