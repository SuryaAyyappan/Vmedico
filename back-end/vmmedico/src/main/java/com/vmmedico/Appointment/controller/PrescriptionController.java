package com.vmmedico.Appointment.controller;

import com.vmmedico.Appointment.dto.PrescriptionRequestDTO;
import com.vmmedico.Appointment.entity.Prescription;
import com.vmmedico.Appointment.service.PrescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generatePrescription(@RequestBody PrescriptionRequestDTO req) {
        try {
            Prescription saved = prescriptionService.generateAndSendPrescription(req);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Prescription generated and emailed",
                    "prescriptionId", saved.getId(),
                    "filePath", saved.getFilePath()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
}
