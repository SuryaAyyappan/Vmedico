package com.vmmedico.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabProfileDTO {
    private Long id;
    private String labName;
    private String registrationNumber;
    private String licenseNumber;
    private String phoneNumber;
    private String address;
}

