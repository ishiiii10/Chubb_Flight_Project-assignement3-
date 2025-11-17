package com.chubb.FlightBookingSystem.repository;

import com.chubb.FlightBookingSystem.entity.TripBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepo extends JpaRepository<TripBooking, Long> {

    Optional<TripBooking> findByBookingRef(String bookingRef);

    boolean existsByBookingRef(String bookingRef);
}