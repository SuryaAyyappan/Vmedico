package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.Doctor;

public interface DoctorService {
    Doctor saveDoctor(Doctor doctor);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByLicenseNumber(String licenseNumber);
//    Doctor findAll(Doctor doctor);
}
