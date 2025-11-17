package com.chubb.FlightBookingSystem.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "passenger_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerDetails extends AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String gender;   // "MALE", "FEMALE", "OTHER"

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, length = 10)
    private String mealType;   // "VEG" / "NON_VEG"

    @Column(nullable = false, length = 5)
    private String seatNumber; // e.g. "12A"

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private TripBooking booking;
}