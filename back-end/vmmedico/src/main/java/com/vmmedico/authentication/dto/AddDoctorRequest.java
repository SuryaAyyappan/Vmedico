package com.vmmedico.authentication.dto;

import lombok.Data;

@Data
public class AddDoctorRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String specialization;
    private String qualification;
    private String licenseNumber;
    private String gender;
    private String dob;
    private Double consultationFee;
    private Integer experience;
}
