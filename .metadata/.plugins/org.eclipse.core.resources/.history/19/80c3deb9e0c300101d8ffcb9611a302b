package com.chubb.FlightBookingSystem.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false, unique = true, length = 5)
    private String airlineCode;  // My custom code: ex: "SKY1"

    @Column(nullable = false)
    private String airlineName;  // ex: "Sky High Airways"

    private String country;

    private String supportEmail;

    @Builder.Default
    private boolean active = true;
}
