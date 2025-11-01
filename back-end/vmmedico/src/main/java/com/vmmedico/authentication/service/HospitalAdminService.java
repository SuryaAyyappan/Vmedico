package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.HospitalAdmin;
import com.vmmedico.authentication.entity.User;

import java.util.Optional;

public interface HospitalAdminService {
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    HospitalAdmin saveHospitalAdmin(HospitalAdmin hospitalAdmin);
    Optional<HospitalAdmin> findById(Long id);
    Optional<HospitalAdmin> findByUser(User user);
}
