package com.chubb.FlightBookingSystem.controller;

import com.chubb.FlightBookingSystem.dto.BookingRequest;
import com.chubb.FlightBookingSystem.dto.FlightRequest;
import com.chubb.FlightBookingSystem.dto.FlightSearchRequest;
import com.chubb.FlightBookingSystem.entity.FlightInfo;
import com.chubb.FlightBookingSystem.entity.TripBooking;
import com.chubb.FlightBookingSystem.repository.BookingRepo;
import com.chubb.FlightBookingSystem.service.AirlineService;
import com.chubb.FlightBookingSystem.service.BookingService;
import com.chubb.FlightBookingSystem.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller added to align with the case-study style endpoints:
 *
 *  - POST   /api/v1.0/flight/airline/inventory/add
 *  - POST   /api/v1.0/flight/search
 *  - POST   /api/v1.0/flight/booking/{flightId}
 *  - GET    /api/v1.0/flight/ticket/{pnr}
 *  - GET    /api/v1.0/flight/booking/history/{emailId}
 *  - DELETE /api/v1.0/flight/booking/cancel/{pnr}
 *
 * Internally it reuses the existing services and DTOs that you already built.
 */
@RestController
@RequestMapping("/api/v1.0/flight")
public class FlightCaseStudyController {

    @Autowired
    private AirlineService airlineService;   // kept in case you want to use later

    @Autowired
    private FlightService flightService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepo bookingRepo;

    /**
     * Add flight inventory for an airline.
     * Maps to:
     *   POST /api/v1.0/flight/airline/inventory/add
     *
     * Uses the same FlightRequest DTO and FlightService.createFlight().
     */
    @PostMapping("/airline/inventory/add")
    public ResponseEntity<FlightInfo> addInventory(@Valid @RequestBody FlightRequest request) {
        FlightInfo flight = flightService.createFlight(request);
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    /**
     * Search flights based on from/to/date/passengers.
     * Maps to:
     *   POST /api/v1.0/flight/search
     *
     * Uses FlightSearchRequest and FlightService.searchFlights().
     */
    @PostMapping("/search")
    public ResponseEntity<Object> searchFlights(
            @Valid @RequestBody FlightSearchRequest search) {

        Object result = flightService.searchFlights(search);
        return ResponseEntity.ok(result);
    }

    /**
     * Create a booking for a given flight id.
     * Maps to:
     *   POST /api/v1.0/flight/booking/{flightId}
     *
     * It reuses your BookingRequest DTO — we just push the path flightId into it
     * and delegate to BookingService.createBooking().
     */
    @PostMapping("/booking/{flightId}")
    public ResponseEntity<TripBooking> bookFlight(@PathVariable Long flightId,
                                                  @Valid @RequestBody BookingRequest request) {
        request.setFlightId(flightId);
        TripBooking booking = bookingService.createBooking(request);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    /**
     * View ticket (booking) details by PNR.
     * Maps to:
     *   GET /api/v1.0/flight/ticket/{pnr}
     *
     * Under the hood it uses BookingRepo.findByBookingRef(pnr).
     */
    @GetMapping("/ticket/{pnr}")
    public ResponseEntity<TripBooking> getTicket(@PathVariable String pnr) {
        Optional<TripBooking> bookingOpt = bookingRepo.findByBookingRef(pnr);
        return bookingOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * View history of bookings for a given email id.
     * Maps to:
     *   GET /api/v1.0/flight/booking/history/{emailId}
     *
     * Internally this just calls BookingService.getBookingsForTraveler(emailId).
     */
    @GetMapping("/booking/history/{emailId}")
    public ResponseEntity<List<TripBooking>> getBookingHistory(@PathVariable String emailId) {
        List<TripBooking> bookings = bookingService.getBookingsForTraveler(emailId);
        return ResponseEntity.ok(bookings);
    }

    /**
     * Cancel a ticket using PNR.
     * Maps to:
     *   DELETE /api/v1.0/flight/booking/cancel/{pnr}
     *
     * Delegates to BookingService.cancelBooking(pnr) which already
     * restores seats and enforces business rules.
     */
    @DeleteMapping("/booking/cancel/{pnr}")
    public ResponseEntity<TripBooking> cancelBookingCase(@PathVariable String pnr) {
        TripBooking cancelled = bookingService.cancelBooking(pnr);
        return ResponseEntity.ok(cancelled);
    }
}