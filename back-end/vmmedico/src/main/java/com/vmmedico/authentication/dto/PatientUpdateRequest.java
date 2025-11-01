package com.vmmedico.authentication.dto;

import com.vmmedico.authentication.enums.Gender;
import lombok.Data;

@Data
public class PatientUpdateRequest {
    private String name;
    private Gender gender;   // reuse your enum
    private String dob;      // keep same format as stored
    private String address;
    private String bloodGroup;
    private String emergencyContact; // allowed to change
}
