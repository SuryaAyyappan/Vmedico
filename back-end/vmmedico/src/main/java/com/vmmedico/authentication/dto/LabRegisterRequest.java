package com.vmmedico.authentication.dto;

import lombok.Data;

@Data
public class LabRegisterRequest {
    private String labName;
    private String registrationNumber;
    private String licenseNumber;
    private String address;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;
    private String role; // Should be "LAB"
}
