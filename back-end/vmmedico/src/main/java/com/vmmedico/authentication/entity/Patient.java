package com.vmmedico.authentication.entity;

import com.vmmedico.common.Gender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private String dob;
    private String address;
    private String bloodGroup;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String emergencyContact;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
