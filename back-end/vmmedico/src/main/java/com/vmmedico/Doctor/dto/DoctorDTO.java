package com.vmmedico.Doctor.dto;

import com.vmmedico.common.Gender;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Long id;
    private String name;
    private String specialization;
    private String qualification;
    private String phoneNumber;
    private String dob;
    private Gender gender;
    private String hospitalName;
    private Double consultationFee;
    private Integer experience;
    private Long userId;
}
