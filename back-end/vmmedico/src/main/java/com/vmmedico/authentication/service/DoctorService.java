package com.vmmedico.authentication.service;

import com.vmmedico.Doctor.dto.DoctorDTO;
import com.vmmedico.authentication.entity.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor saveDoctor(Doctor doctor);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    List<DoctorDTO> getAllDoctors();
}
