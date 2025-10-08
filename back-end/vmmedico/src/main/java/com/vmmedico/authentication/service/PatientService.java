//package com.vmmedico.vmmedico.service;
//
//import com.vmmedico.vmmedico.entity.Patient;
//import com.vmmedico.vmmedico.repository.PatientRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class PatientService {
//
//    @Autowired
//    private PatientRepository patientRepository;
//
//    public Patient savePatient(Patient patient) {
//        return patientRepository.save(patient);
//    }
//
//    public Optional<Patient> findById(Long id) {
//        return patientRepository.findById(id);
//    }
//
//    public boolean phoneExists(String phoneNumber) {
//        return patientRepository.existsByPhoneNumber(phoneNumber);
//    }
//
//    public boolean emergencyContactExists(String emergencyContact) {
//        return patientRepository.existsByEmergencyContact(emergencyContact);
//    }
//
//    // Optional: Combined check
//    public boolean isPhoneOrEmergencyExists(String phoneNumber, String emergencyContact) {
//        return phoneExists(phoneNumber) || emergencyContactExists(emergencyContact);
//    }
//}
package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.Patient;
import com.vmmedico.authentication.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Save patient
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Check if phone number exists
    public boolean existsByPhoneNumber(String phoneNumber) {
        return patientRepository.existsByPhoneNumber(phoneNumber);
    }

    // Check if emergency contact exists
    public boolean existsByEmergencyContact(String emergencyContact) {
        return patientRepository.existsByEmergencyContact(emergencyContact);
    }

    // Find patient by ID
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }
}
