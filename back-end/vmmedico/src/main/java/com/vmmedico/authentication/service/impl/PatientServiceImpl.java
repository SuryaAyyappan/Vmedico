package com.vmmedico.authentication.service.impl;

import com.vmmedico.authentication.entity.Patient;
import com.vmmedico.authentication.repository.PatientRepository;
import com.vmmedico.authentication.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return patientRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByEmergencyContact(String emergencyContact) {
        return patientRepository.existsByEmergencyContact(emergencyContact);
    }
}
