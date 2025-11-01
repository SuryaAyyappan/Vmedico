package com.vmmedico.authentication.service.impl;

import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.entity.User;
import com.vmmedico.authentication.repository.LabRepository;
import com.vmmedico.authentication.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabServiceImpl implements LabService {

    @Autowired
    private LabRepository labRepository;

    // ✅ Simulated valid registration and license numbers
    private static final List<String> VALID_REG_NUMBERS = List.of(
            "LAB1001", "LAB1002", "LAB1003", "LAB1004", "LAB1005",
            "LAB1006", "LAB1007", "LAB1008", "LAB1009", "LAB1010"
    );

    private static final List<String> VALID_LICENSE_NUMBERS = List.of(
            "LIC3001", "LIC3002", "LIC3003", "LIC3004", "LIC3005",
            "LIC3006", "LIC3007", "LIC3008", "LIC3009", "LIC3010"
    );

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return labRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByRegistrationNumber(String registrationNumber) {
        return VALID_REG_NUMBERS.contains(registrationNumber);
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return VALID_LICENSE_NUMBERS.contains(licenseNumber);
    }

    @Override
    public Lab saveLab(Lab lab) {
        return labRepository.save(lab);
    }
    @Override
    public Optional<Lab> findByUser(User user) {
        return labRepository.findByUser(user);
    }

    @Override
    public Optional<Lab> findById(Long userId) {
        return labRepository.findById(userId);
    }
}
