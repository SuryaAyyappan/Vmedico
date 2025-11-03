package com.vmmedico.authentication.service.impl;

import com.vmmedico.Doctor.dto.DoctorDTO;
import com.vmmedico.authentication.entity.Doctor;
import com.vmmedico.authentication.entity.User;
import com.vmmedico.authentication.repository.DoctorRepository;
import com.vmmedico.authentication.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return doctorRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByLicenseNumber(String licenseNumber) {
        return doctorRepository.existsByLicenseNumber(licenseNumber);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doc -> DoctorDTO.builder()
                        .id(doc.getId())
                        .name(doc.getName())
                        .specialization(doc.getSpecialization())
                        .qualification(doc.getQualification())
                        .phoneNumber(doc.getPhoneNumber())
                        .dob(doc.getDob())
                        .gender(doc.getGender())
                        .hospitalName(doc.getHospitalName())
                        .consultationFee(doc.getConsultationFee())
                        .experience(doc.getExperience())
                        .userId(doc.getUser() != null ? doc.getUser().getId() : null)
                        .build())
                .toList();
    }

    @Override
    public List<DoctorDTO> getAllDoctorsbyname(String hospitalName) {
        return doctorRepository.findByHospitalName(hospitalName)
                .stream()
                .map(doc -> DoctorDTO.builder()
                        .id(doc.getId())
                        .name(doc.getName())
                        .specialization(doc.getSpecialization())
                        .qualification(doc.getQualification())
                        .phoneNumber(doc.getPhoneNumber())
                        .dob(doc.getDob())
                        .gender(doc.getGender()) // ✅ pulled from Doctor entity
                        .hospitalName(doc.getHospitalName()) // ✅ added field
                        .consultationFee(doc.getConsultationFee())
                        .experience(doc.getExperience())
                        .userId(doc.getUser() != null ? doc.getUser().getId() : null)
                        .build())
                .toList();

    }

    @Override
    public Optional<Doctor> findByUser(User user) {
        return doctorRepository.findByUser(user);
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }
}
