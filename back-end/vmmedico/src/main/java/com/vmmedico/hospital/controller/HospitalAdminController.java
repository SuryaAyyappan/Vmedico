package com.vmmedico.hospital.controller;

import com.vmmedico.Appointment.dto.PatientVisitDTO;
import com.vmmedico.Doctor.dto.DoctorDTO;
import com.vmmedico.hospital.service.HospitalAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital-admin")
@RequiredArgsConstructor
public class HospitalAdminController {

    private final HospitalAdminService hospitalAdminService;

    // GET: List of doctors under this hospital
    @GetMapping("/{hospitalName}/doctors")
    public ResponseEntity<List<DoctorDTO>> getDoctors(@PathVariable String hospitalName) {
        return ResponseEntity.ok(hospitalAdminService.getDoctorsByHospital(hospitalName));
    }

    // GET: List of patients who visited any doctor in this hospital
    @GetMapping("/{hospitalName}/patients")
    public ResponseEntity<List<PatientVisitDTO>> getPatients(@PathVariable String hospitalName) {
        return ResponseEntity.ok(hospitalAdminService.getPatientVisitsByHospital(hospitalName));
    }
}
