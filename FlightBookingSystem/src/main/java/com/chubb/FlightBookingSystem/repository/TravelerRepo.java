package com.chubb.FlightBookingSystem.repository;

import com.chubb.FlightBookingSystem.entity.TravelerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelerRepo extends JpaRepository<TravelerProfile, Long> {

    Optional<TravelerProfile> findByEmail(String email);

    boolean existsByEmail(String email);
}