package com.vmmedico.authentication.dto;

import lombok.Data;

@Data
public class HospitalAdminRegisterRequest {
    private String hospitalName;
    private String registrationNumber;
    private String licenseNumber;
    private String address;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;
    private String role; // Should be "HOSPITAL_ADMIN"
}
