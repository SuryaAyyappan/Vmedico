package com.vmmedico.authentication.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private String name;
    private String hospitalName;
    private String designation;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
