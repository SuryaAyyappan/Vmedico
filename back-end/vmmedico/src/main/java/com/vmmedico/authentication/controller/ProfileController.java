package com.vmmedico.authentication.controller;

import com.vmmedico.authentication.configuration.JwtTokenUtil;
import com.vmmedico.authentication.dto.*;
import com.vmmedico.authentication.entity.*;
import com.vmmedico.authentication.enums.Role;
import com.vmmedico.authentication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private HospitalAdminService hospitalAdminService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private LabService labService;


    @GetMapping
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // remove "Bearer "
            if (!jwtTokenUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("Invalid or expired token");
            }

            Long userId = jwtTokenUtil.getUserIdFromToken(token);
            String roleName = jwtTokenUtil.getRoleFromToken(token).toUpperCase();
            Role role = Role.valueOf(roleName);


            Optional<User> userOpt = userService.findById(userId);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }
            User user = userOpt.get();


            switch (role) {
                case DOCTOR -> {
                    Optional<Doctor> docOpt = doctorService.findByUser(user);
                    if (docOpt.isEmpty()) {
                        return ResponseEntity.status(404).body("Doctor profile not found");
                    }
                    Doctor doc = docOpt.get();
                    DoctorProfileDTO dto = new DoctorProfileDTO(
                            doc.getName(),
                            doc.getSpecialization(),
                            doc.getLicenseNumber(),
                            doc.getPhoneNumber(),
                            doc.getQualification(),
                            doc.getGender(),
                            doc.getDob(),
                            doc.getHospitalName()
                    );
                    return ResponseEntity.ok(dto);
                }

                case HOSPITAL_ADMIN -> {
                    Optional<HospitalAdmin> adminOpt = hospitalAdminService.findByUser(user);
                    if (adminOpt.isEmpty()) {
                        return ResponseEntity.status(404).body("Hospital admin profile not found");
                    }
                    HospitalAdmin admin = adminOpt.get();
                    HospitalAdminProfileDTO dto = new HospitalAdminProfileDTO(
                            admin.getId(),
                            admin.getHospitalName(),
                            admin.getRegistrationNumber(),
                            admin.getLicenseNumber(),
                            admin.getPhoneNumber(),
                            admin.getAddress(),
                            admin.getVerified()
                    );
                    return ResponseEntity.ok(dto);
                }

                case PATIENT -> {
                    Optional<Patient> patientOpt = patientService.findByUser(user);
                    if (patientOpt.isEmpty()) {
                        return ResponseEntity.status(404).body("Patient profile not found");
                    }
                    Patient patient = patientOpt.get();
                    PatientProfileDTO dto = new PatientProfileDTO(
                            patient.getId(),
                            patient.getName(),
                            patient.getGender(),
                            patient.getDob(),
                            patient.getAddress(),
                            patient.getEmergencyContact()
                    );
                    return ResponseEntity.ok(dto);
                }

                case LAB -> {
                    Optional<Lab> labOpt = labService.findByUser(user);
                    if (labOpt.isEmpty()) {
                        return ResponseEntity.status(404).body("Lab profile not found");
                    }
                    Lab lab = labOpt.get();
                    LabProfileDTO dto = new LabProfileDTO(
                            lab.getId(),
                            lab.getLabName(),
                            lab.getRegistrationNumber(),
                            lab.getLicenseNumber(),
                            lab.getPhoneNumber(),
                            lab.getAddress()
                    );
                    return ResponseEntity.ok(dto);
                }

                default -> {
                    return ResponseEntity.status(400).body("Invalid role type in token");
                }
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error retrieving profile: " + e.getMessage());
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updatePatientProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody PatientProfileDTO updatedDTO
    ) {
        try {
            String token = authHeader.substring(7);
            if (!jwtTokenUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("Invalid or expired token");
            }

            Long userId = jwtTokenUtil.getUserIdFromToken(token);
            String roleName = jwtTokenUtil.getRoleFromToken(token);

            if (!roleName.equalsIgnoreCase("PATIENT")) {
                return ResponseEntity.status(403).body("Only patients can update their profile");
            }

            Optional<User> userOpt = userService.findById(userId);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }

            Optional<Patient> patientOpt = patientService.findByUser(userOpt.get());
            if (patientOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Patient profile not found");
            }

            Patient existing = patientOpt.get();
            existing.setName(updatedDTO.getName());
            existing.setGender(updatedDTO.getGender());
            existing.setDob(updatedDTO.getDob());
            existing.setAddress(updatedDTO.getAddress());
            existing.setEmergencyContact(updatedDTO.getEmergencyContact());

            Patient saved = patientService.savePatient(existing);

            PatientProfileDTO savedDTO = new PatientProfileDTO(
                    saved.getId(),
                    saved.getName(),
                    saved.getGender(),
                    saved.getDob(),
                    saved.getAddress(),
                    saved.getEmergencyContact()
            );

            return ResponseEntity.ok(savedDTO);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating profile: " + e.getMessage());
        }
    }
}
