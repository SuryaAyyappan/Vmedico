package com.vmmedico.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalAdminProfileDTO {
    private Long id;
    private String hospitalName;
    private String registrationNumber;
    private String licenseNumber;
    private String phoneNumber;
    private String address;
    private Boolean verified;
}

