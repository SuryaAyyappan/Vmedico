package com.vmmedico.authentication.dto;

import com.vmmedico.common.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProfileDTO {
    private String name;
    private String specialization;
    private String licenseNumber;
    private String phoneNumber;
    private String qualification;
    private Gender gender;
    private String dob;
    private String hospitalName;
}
