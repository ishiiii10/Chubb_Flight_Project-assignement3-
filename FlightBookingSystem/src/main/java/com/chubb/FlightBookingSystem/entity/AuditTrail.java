package com.chubb.FlightBookingSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Simple audit info I want to store for every table.
 * Used instead of repeating created/updated fields everywhere.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AuditTrail {

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String createdBy = "system";
    private String updatedBy = "system";

    @PrePersist
    public void markCreated() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }
}