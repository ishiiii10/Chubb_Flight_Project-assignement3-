package com.chubb.FlightBookingSystem.repository;

import com.chubb.FlightBookingSystem.entity.FlightInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepo extends JpaRepository<FlightInfo, Long> {

    List<FlightInfo> findByFromAirportAndToAirportAndDepartureTimeBetween(
            String fromAirport,
            String toAirport,
            LocalDateTime start,
            LocalDateTime end
    );

    boolean existsByFlightCode(String flightCode); // <--- add this
}