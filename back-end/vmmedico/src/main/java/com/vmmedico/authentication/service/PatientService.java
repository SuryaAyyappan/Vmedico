package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.Patient;

public interface PatientService {

    Patient savePatient(Patient patient);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmergencyContact(String emergencyContact);
}
