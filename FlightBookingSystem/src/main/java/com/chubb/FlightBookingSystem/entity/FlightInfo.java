package com.chubb.FlightBookingSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flight_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInfo extends AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Example: "FB101", "KIIT202"
    @Column(nullable = false, unique = true, length = 10)
    private String flightCode;

    // Link each flight to an airline
    @ManyToOne(optional = false)
    @JoinColumn(name = "airline_id")
    private AirlineDetails airline;

    @Column(nullable = false, length = 3)
    private String fromAirport;   // e.g. "DEL"

    @Column(nullable = false, length = 3)
    private String toAirport;     // e.g. "BOM"

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    @Column(nullable = false)
    private Double baseFare;

    @Column(nullable = false)
    private String status;   // e.g. "SCHEDULED", "CANCELLED"
}