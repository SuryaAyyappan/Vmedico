package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.entity.User;

import java.util.Optional;

public interface LabService {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    Lab saveLab(Lab lab);
    Optional<Lab> findByUser(User user);
    Optional<Lab> findById(Long userId);
}
