package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.Patient;
import com.vmmedico.authentication.entity.User;

import java.util.Optional;

public interface PatientService {

    Patient savePatient(Patient patient);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmergencyContact(String emergencyContact);
    Optional<Patient> findByUser(User user);
    Optional<Patient> findById(Long userId);
}
