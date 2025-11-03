package com.vmmedico.authentication.service;

import com.vmmedico.Doctor.dto.DoctorDTO;
import com.vmmedico.authentication.entity.Doctor;
import com.vmmedico.authentication.entity.User;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor saveDoctor(Doctor doctor);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    Optional<Doctor> findByUser(User user);
    Optional<Doctor> findById(Long id);

    List<DoctorDTO> getAllDoctors();
    List<DoctorDTO> getAllDoctorsbyname(String hospitalName);
}
