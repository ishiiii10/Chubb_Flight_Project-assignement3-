

package com.chubb.FlightBookingSystem.dto;

import jakarta.validation.constraints.*;
import java.util.List;
//...
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Data I need from the client to create a booking.
 */
@Getter
@Setter
public class BookingRequest {

    // traveler details
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is not valid")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Size(min = 8, max = 15, message = "Mobile number length must be between 8 and 15")
    private String mobileNumber;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "ID document number is required")
    private String idDocumentNumber;

    // booking details
    @NotNull(message = "Flight id is required")
    @Positive(message = "Flight id must be positive")
    private Long flightId;

    @NotNull(message = "Seats to book is required")
    @Min(value = 1, message = "At least 1 seat must be booked")
    @Max(value = 9, message = "Cannot book more than 9 seats in one booking")
    private Integer seatsToBook;
    
    @NotNull(message = "Passenger list is required")
    @Size(min = 1, message = "At least one passenger is required")
    private List<PassengerRequest> passengers;
}