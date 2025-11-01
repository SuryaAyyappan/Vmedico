package com.vmmedico.authentication.repository;

import com.vmmedico.authentication.entity.Patient;
import com.vmmedico.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmergencyContact(String emergencyContact);
    Optional<Patient> findByUser(User user);

    Optional<Patient> findByUserId(Long userId);
}
