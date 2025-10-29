package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.Lab;

public interface LabService {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    Lab saveLab(Lab lab);
}
