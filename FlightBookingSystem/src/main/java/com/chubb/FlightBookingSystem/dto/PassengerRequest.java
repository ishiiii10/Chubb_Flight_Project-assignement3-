package com.chubb.FlightBookingSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerRequest {

    @NotBlank(message = "Passenger name is required")
    private String name;

    @NotBlank(message = "Gender is required")
    private String gender;  // "MALE", "FEMALE", "OTHER"

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 120, message = "Age seems invalid")
    private Integer age;

    @NotBlank(message = "Meal type is required")
    private String mealType;   // "VEG" / "NON_VEG"

    @NotBlank(message = "Seat number is required")
    private String seatNumber; // e.g. "12A"
}