//package com.vmmedico.vmmedico.service;
//
//import com.vmmedico.vmmedico.entity.Doctor;
//import com.vmmedico.vmmedico.repository.DoctorRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class DoctorService {
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    public Doctor saveDoctor(Doctor doctor) {
//        return doctorRepository.save(doctor);
//    }
//
//    public Optional<Doctor> findById(Long id) {
//        return doctorRepository.findById(id);
//    }
//
//    public boolean phoneExists(String phoneNumber) {
//        return doctorRepository.existsByPhoneNumber(phoneNumber);
//    }
//}
package com.vmmedico.authentication.service;

import com.vmmedico.Doctor.dto.DoctorDTO;
import com.vmmedico.authentication.entity.Doctor;
import com.vmmedico.authentication.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Save doctor
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Check if phone number exists
    public boolean existsByPhoneNumber(String phoneNumber) {
        return doctorRepository.existsByPhoneNumber(phoneNumber);
    }

    // Find doctor by ID
    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doc -> DoctorDTO.builder()
                        .id(doc.getId())
                        .name(doc.getName())
                        .specialization(doc.getSpecialization())
                        .qualification(doc.getQualification())
                        .phoneNumber(doc.getPhoneNumber())
                        .gender(doc.getGender())
                        .dob(doc.getDob())
                        .userId(doc.getUser()!=null?doc.getUser().getId():null)
                        .build())
                .toList();
    }
}
