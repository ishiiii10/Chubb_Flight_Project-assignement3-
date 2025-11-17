package com.chubb.FlightBookingSystem.repository;

import com.chubb.FlightBookingSystem.entity.TripBooking;
import com.chubb.FlightBookingSystem.entity.TravelerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<TripBooking, Long> {

    Optional<TripBooking> findByBookingRef(String bookingRef);

    boolean existsByBookingRef(String bookingRef);

    List<TripBooking> findByTraveler(TravelerProfile traveler);   // <--- add this
}