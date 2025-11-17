package com.chubb.FlightBookingSystem.service;

import com.chubb.FlightBookingSystem.entity.AirlineDetails;
import com.chubb.FlightBookingSystem.exception.AirlineAlreadyExistsException;
import com.chubb.FlightBookingSystem.repository.AirlineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepo airlineRepo;

    // Add a new airline with basic duplicate check
    public AirlineDetails addAirline(AirlineDetails airlineDetails) {
        String code = airlineDetails.getAirlineCode();

        if (airlineRepo.existsByAirlineCode(code)) {
            throw new AirlineAlreadyExistsException(code);
        }

        return airlineRepo.save(airlineDetails);
    }

    // Get all airlines (will be useful for UI / testing)
    public List<AirlineDetails> getAllAirlines() {
        return airlineRepo.findAll();
    }
}
