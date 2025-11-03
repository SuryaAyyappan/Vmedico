package com.vmmedico.authentication.dto;

import com.vmmedico.common.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientProfileDTO {
    private Long id;
    private String name;
    private Gender gender;
    private String dob;
    private String address;
    private String emergencyContact;
}
