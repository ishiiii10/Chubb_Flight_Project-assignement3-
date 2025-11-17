package com.chubb.FlightBookingSystem.entity;

import jakarta.persistence.*;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "trip_booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripBooking extends AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique booking reference — can be shared with user
    @Column(nullable = false, unique = true)
    @JsonProperty("pnr")
    private String bookingRef;

    @ManyToOne(optional = false)
    @JoinColumn(name = "traveler_id")
    private TravelerProfile traveler;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    private FlightInfo flight;

    @Column(nullable = false)
    private Integer seatsBooked;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private String status;  // CONFIRMED, CANCELLED
}