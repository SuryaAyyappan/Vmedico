package com.vmmedico.lab.entity;

import com.vmmedico.Appointment.entity.LabBooking;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "lab_reports")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LabReport {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private LabBooking booking;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Instant uploadedAt;

    private String uploadedBy; // username of lab admin
}
