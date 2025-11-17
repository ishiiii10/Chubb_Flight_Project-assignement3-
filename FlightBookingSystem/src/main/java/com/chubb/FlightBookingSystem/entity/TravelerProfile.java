package com.chubb.FlightBookingSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "traveler_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelerProfile extends AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Full name shown on the ticket
    @Column(nullable = false)
    private String fullName;

    // This will be unique so user can search bookings by email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 15)
    private String mobileNumber;

    // Just for fun / realism
    private String country;

    private String idDocumentNumber;   // e.g. Aadhaar / Passport number
}