package com.vmmedico.authentication.repository;

import com.vmmedico.authentication.entity.HospitalAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalAdminRepository extends JpaRepository<HospitalAdmin, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    Optional<HospitalAdmin> findByUserId(Long userId);
}
