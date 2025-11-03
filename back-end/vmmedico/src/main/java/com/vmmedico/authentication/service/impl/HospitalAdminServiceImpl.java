package com.vmmedico.authentication.service.impl;

import com.vmmedico.authentication.entity.HospitalAdmin;
import com.vmmedico.authentication.entity.User;
import com.vmmedico.authentication.repository.HospitalAdminRepository;
import com.vmmedico.authentication.service.HospitalAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalAdminServiceImpl implements HospitalAdminService {

    @Autowired
    private HospitalAdminRepository hospitalAdminRepository;

    // Simulated valid registration and license numbers
    private static final List<String> VALID_REG_NUMBERS = List.of(
            "REG10001", "REG10002", "REG10003", "REG10004", "REG10005",
            "REG10006", "REG10007", "REG10008", "REG10009", "REG10010"
    );

    private static final List<String> VALID_LICENSE_NUMBERS = List.of(
            "LIC20001", "LIC20002", "LIC20003", "LIC20004", "LIC20005",
            "LIC20006", "LIC20007", "LIC20008", "LIC20009", "LIC20010"
    );

    @Override
    public boolean existsByRegistrationNumber(String registrationNumber) {
        return VALID_REG_NUMBERS.contains(registrationNumber);
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return VALID_LICENSE_NUMBERS.contains(licenseNumber);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return hospitalAdminRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public HospitalAdmin saveHospitalAdmin(HospitalAdmin hospitalAdmin) {
        return hospitalAdminRepository.save(hospitalAdmin);
    }
    @Override
    public Optional<HospitalAdmin> findById(Long id) {
        return hospitalAdminRepository.findById(id);
    }
    @Override
    public Optional<HospitalAdmin> findByUser(User user) {
        return hospitalAdminRepository.findByUser(user);
    }
}
