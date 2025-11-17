package com.chubb.FlightBookingSystem.service;

import com.chubb.FlightBookingSystem.dto.BookingRequest;
import com.chubb.FlightBookingSystem.entity.FlightInfo;
import com.chubb.FlightBookingSystem.entity.TripBooking;
import com.chubb.FlightBookingSystem.entity.TravelerProfile;
import com.chubb.FlightBookingSystem.exception.FlightNotFoundException;
import com.chubb.FlightBookingSystem.exception.NotEnoughSeatsException;
import com.chubb.FlightBookingSystem.repository.BookingRepo;
import com.chubb.FlightBookingSystem.repository.FlightRepo;
import com.chubb.FlightBookingSystem.repository.TravelerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TravelerRepo travelerRepo;

    @Autowired
    private FlightRepo flightRepo;

    public TripBooking createBooking(BookingRequest request) {

        // 1) Get the flight
        FlightInfo flight = flightRepo.findById(request.getFlightId())
                .orElseThrow(() -> new FlightNotFoundException(request.getFlightId()));

        // 2) Validate seats
        int requestedSeats = request.getSeatsToBook();
        if (requestedSeats <= 0) {
            throw new IllegalArgumentException("Seats to book must be greater than zero");
        }
        if (requestedSeats > flight.getAvailableSeats()) {
            throw new NotEnoughSeatsException(requestedSeats, flight.getAvailableSeats());
        }

        // 3) Find or create traveler by email
        TravelerProfile traveler = travelerRepo.findByEmail(request.getEmail())
                .orElseGet(() -> TravelerProfile.builder()
                        .fullName(request.getFullName())
                        .email(request.getEmail())
                        .mobileNumber(request.getMobileNumber())
                        .country(request.getCountry())
                        .idDocumentNumber(request.getIdDocumentNumber())
                        .build()
                );

        // if it's a new traveler (no id yet), save it
        if (traveler.getId() == null) {
            traveler = travelerRepo.save(traveler);
        }

        // 4) Calculate price (very simple: seats * baseFare)
        double totalPrice = requestedSeats * flight.getBaseFare();

        // 5) Generate a booking reference (simple but unique enough)
        String bookingRef = "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // 6) Reduce available seats on the flight
        flight.setAvailableSeats(flight.getAvailableSeats() - requestedSeats);
        flightRepo.save(flight);

        // 7) Create and save booking
        TripBooking booking = TripBooking.builder()
                .bookingRef(bookingRef)
                .traveler(traveler)
                .flight(flight)
                .seatsBooked(requestedSeats)
                .totalPrice(totalPrice)
                .status("CONFIRMED")
                .build();

        return bookingRepo.save(booking);
    }
}