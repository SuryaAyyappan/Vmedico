package com.vmmedico.lab.controller;

import com.vmmedico.lab.dto.LabBookingDTO;
import com.vmmedico.lab.service.LabReportService;
import com.vmmedico.lab.repo.LabReportRepository;
import com.vmmedico.Appointment.repo.LabBookingRepository;
import com.vmmedico.authentication.service.UserService;
import com.vmmedico.authentication.configuration.JwtTokenUtil;
import com.vmmedico.Appointment.entity.LabBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin
public class LabReportController {

    @Autowired private LabReportService reportService;
    @Autowired private LabReportRepository reportRepo;
    @Autowired private LabBookingRepository bookingRepo;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private UserService userService;

    /**
     * ✅ Patient can list all lab reports for completed bookings
     */
    @GetMapping("/my")
    public ResponseEntity<?> myReports(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtTokenUtil.validateToken(token))
            return ResponseEntity.status(401).body("Invalid token");

        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        // Use DTO to avoid lazy loading errors
        List<LabBookingDTO> dtoList = bookingRepo
                .findAllByPatientUserIdAndReportUploadedTrue(userId)
                .stream()
                .map(b -> LabBookingDTO.builder()
                        .id(b.getId())
                        .testName(b.getLabTest().getName())
                        .labName(b.getLab().getLabName())
                        .patientName(b.getPatient().getName())
                        .date(b.getDate())
                        .reportUploaded(b.getReportUploaded())
                        .reportFileName(b.getReportFileName())
                        .build())
                .toList();

        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("/download/{bookingId}")
    public ResponseEntity<?> downloadReport(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable Long bookingId) {
        String token = authHeader.substring(7);
        if (!jwtTokenUtil.validateToken(token))
            return ResponseEntity.status(401).body("Invalid token");

        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        LabBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getPatient().getUser().getId().equals(userId)) {
            return ResponseEntity.status(403).body(Map.of("error", "Not authorized to access this report"));
        }

        try {
            Resource res = reportService.loadReport(bookingId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + res.getFilename() + "\"")
                    .body(res);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}
