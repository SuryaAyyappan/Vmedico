package com.example.Vmedico.Hospital;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userid;
    private String name;
    private String license;
    private String regno;
    private String status;
}
