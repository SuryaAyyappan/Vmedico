package com.vmmedico.authentication.entity;

import jakarta.persistence.*;
import lombok.*;
import com.vmmedico.authentication.enums.Role;

@Entity
@Table(name = "hospital_admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hospitalName;

    @Column(unique = true, nullable = false)
    private String registrationNumber;

    @Column(unique = true, nullable = false)
    private String licenseNumber;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private String address;

    @Column(nullable = false)
    private Boolean verified = false;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
