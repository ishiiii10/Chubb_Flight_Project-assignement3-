package com.chubb.FlightBookingSystem.service;

import com.chubb.FlightBookingSystem.dto.BookingRequest;

import com.chubb.FlightBookingSystem.entity.FlightInfo;
import com.chubb.FlightBookingSystem.entity.TripBooking;
import com.chubb.FlightBookingSystem.entity.TravelerProfile;
import com.chubb.FlightBookingSystem.exception.FlightNotFoundException;
import com.chubb.FlightBookingSystem.exception.NotEnoughSeatsException;
import com.chubb.FlightBookingSystem.exception.BookingAlreadyCancelledException;
import com.chubb.FlightBookingSystem.exception.BookingNotFoundException;
import com.chubb.FlightBookingSystem.exception.TravelerNotFoundException;
import com.chubb.FlightBookingSystem.repository.BookingRepo;
import com.chubb.FlightBookingSystem.repository.FlightRepo;
import com.chubb.FlightBookingSystem.repository.TravelerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.FlightBookingSystem.exception.CancellationNotAllowedException;
import java.time.LocalDateTime;

import com.chubb.FlightBookingSystem.dto.PassengerRequest;
import com.chubb.FlightBookingSystem.entity.PassengerDetails;
import com.chubb.FlightBookingSystem.repository.PassengerDetailsRepo;

import com.chubb.FlightBookingSystem.util.PnrGenerator;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TravelerRepo travelerRepo;

    @Autowired
    private FlightRepo flightRepo;
    
    @Autowired
    private PassengerDetailsRepo passengerDetailsRepo;

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

        // NEW: passengers list size must match seats
        if (request.getPassengers() == null || request.getPassengers().isEmpty()) {
            throw new IllegalArgumentException("Passenger details are required");
        }
        if (request.getPassengers().size() != requestedSeats) {
            throw new IllegalArgumentException(
                    "Seats to book (" + requestedSeats + ") must match number of passengers (" +
                            request.getPassengers().size() + ")"
            );
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
        
        String pnr;
        do {
            pnr = PnrGenerator.generatePnr();
        } while (bookingRepo.existsByBookingRef(pnr));

        // 6) Reduce available seats on the flight
        flight.setAvailableSeats(flight.getAvailableSeats() - requestedSeats);
        flightRepo.save(flight);

        // 7) Create and save booking
        TripBooking booking = TripBooking.builder()
                .bookingRef(pnr)
                .traveler(traveler)
                .flight(flight)
                .seatsBooked(requestedSeats)
                .totalPrice(totalPrice)
                .status("CONFIRMED")
                .build();

        // First save booking to get ID
        booking = bookingRepo.save(booking);

        // Save passenger details
        for (PassengerRequest pReq : request.getPassengers()) {
            PassengerDetails passenger = PassengerDetails.builder()
                    .name(pReq.getName())
                    .gender(pReq.getGender())
                    .age(pReq.getAge())
                    .mealType(pReq.getMealType())
                    .seatNumber(pReq.getSeatNumber())
                    .booking(booking)
                    .build();

            passengerDetailsRepo.save(passenger);
        }

        return booking;
    }
    
    public TripBooking cancelBooking(String pnr) {

        TripBooking booking = bookingRepo.findByBookingRef(pnr)
                .orElseThrow(() -> new BookingNotFoundException(pnr));

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new BookingAlreadyCancelledException(pnr);
        }

        // 24-hour cancellation rule
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departure = booking.getFlight().getDepartureTime();

        // if departure is <= now + 24h, cancellation is NOT allowed
        if (!departure.isAfter(now.plusHours(24))) {
            throw new CancellationNotAllowedException(pnr);
        }

        // restore seats to the flight
        FlightInfo flight = booking.getFlight();
        int seatsToRestore = booking.getSeatsBooked();
        flight.setAvailableSeats(flight.getAvailableSeats() + seatsToRestore);
        flightRepo.save(flight);

        // update booking status
        booking.setStatus("CANCELLED");

        return bookingRepo.save(booking);
    }
    
    public java.util.List<TripBooking> getBookingsForTraveler(String email) {

        // 1) Find traveler by email
        TravelerProfile traveler = travelerRepo.findByEmail(email)
                .orElseThrow(() -> new TravelerNotFoundException(email));

        // 2) Find bookings for that traveler
        return bookingRepo.findByTraveler(traveler);
    }
}