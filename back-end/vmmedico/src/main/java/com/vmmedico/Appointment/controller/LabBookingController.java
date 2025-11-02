package com.vmmedico.Appointment.controller;

import com.vmmedico.Appointment.dto.LabBookingRequestDTO;
import com.vmmedico.Appointment.service.LabBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lab-bookings")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LabBookingController {

    private final LabBookingService labBookingService;

    @PostMapping("/book")
    public ResponseEntity<?> bookLabTest(@RequestBody LabBookingRequestDTO request) {
        if (request.getLabUserId() == null ||
                request.getPatientUserId() == null ||
                request.getLabTestId() == null ||
                request.getDate() == null ||
                request.getStartTime() == null) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("success", false, "message", "Missing required fields")
            );
        }

        return labBookingService.bookLabTest(
                request.getLabUserId(),
                request.getPatientUserId(),
                request.getLabTestId(),
                request.getDate(),
                request.getStartTime()
        );
    }
}
