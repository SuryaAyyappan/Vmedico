package com.vmmedico.authentication.repository;

import com.vmmedico.authentication.entity.HospitalAdmin;
import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabRepository extends JpaRepository<Lab, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    Optional<Lab> findByUser(User user);
    Optional<Lab> findById(Long userId);


}
