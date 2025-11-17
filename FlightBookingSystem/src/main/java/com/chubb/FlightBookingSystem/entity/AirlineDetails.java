package com.chubb.FlightBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "airline_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineDetails extends AuditTrail {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Airline code is required")
    @Size(max = 5)
    @Column(nullable = false, unique = true, length = 5)
    private String airlineCode;

    @NotBlank(message = "Airline name is required")
    @Column(nullable = false)
    private String airlineName;

    private String country;

    @Email(message = "Support email should be valid")
    private String supportEmail;

    @Builder.Default
    private boolean active = true;
}


