package com.chubb.FlightBookingSystem.repository;

import com.chubb.FlightBookingSystem.entity.PassengerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerDetailsRepo extends JpaRepository<PassengerDetails, Long> {
}