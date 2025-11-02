package com.vmmedico.lab.entity;

import com.vmmedico.authentication.entity.Lab;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "lab_tests", uniqueConstraints = @UniqueConstraint(columnNames = {"lab_id", "test_code"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LabTest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id")
    private Lab lab;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(name = "test_code", nullable = false)
    private String testCode; // unique per lab

    private String description;

    @Min(0)
    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean active = true;

}
