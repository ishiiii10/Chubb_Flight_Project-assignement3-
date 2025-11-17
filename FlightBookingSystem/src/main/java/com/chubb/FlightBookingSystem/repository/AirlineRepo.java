package com.chubb.FlightBookingSystem.repository;

import com.chubb.FlightBookingSystem.entity.AirlineDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirlineRepo extends JpaRepository<AirlineDetails, Long> {

    Optional<AirlineDetails> findByAirlineCode(String airlineCode);

    boolean existsByAirlineCode(String airlineCode);
}
