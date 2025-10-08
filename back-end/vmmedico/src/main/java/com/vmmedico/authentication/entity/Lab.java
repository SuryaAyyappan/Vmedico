package com.vmmedico.authentication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "labs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String labName;
    private String registrationNumber;
    private String address;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private String licenseNumber;
}
