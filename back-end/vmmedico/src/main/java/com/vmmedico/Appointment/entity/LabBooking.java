package com.vmmedico.Appointment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.entity.Patient;
import com.vmmedico.lab.entity.LabTest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "lab_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LabBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id", nullable = false)
    @JsonIgnoreProperties({"labBookings", "user", "hibernateLazyInitializer", "handler"})
    private Lab lab;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"labBookings", "user", "hibernateLazyInitializer", "handler"})
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_test_id", nullable = false)
    @JsonIgnoreProperties({"labBookings", "hibernateLazyInitializer", "handler"})
    private LabTest labTest;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.CONFIRMED;

    private Boolean reportUploaded = false;
    private String reportFileName;
    private String remarks;

    public enum BookingStatus {
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }
}