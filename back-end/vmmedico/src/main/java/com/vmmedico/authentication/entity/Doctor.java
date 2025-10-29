package com.vmmedico.authentication.entity;

import com.vmmedico.common.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private String licenseNumber;

    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String qualification;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender; // use the enum type, not String


    @Column(nullable = false)
    private String dob;


}
