package com.vmmedico.lab.controller;

import com.vmmedico.Appointment.dto.LabBookingResponseDTO;
import com.vmmedico.Appointment.entity.LabBooking;
import com.vmmedico.Appointment.repo.LabBookingRepository;
import com.vmmedico.authentication.entity.User;
import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.service.UserService;
import com.vmmedico.authentication.service.LabService;
import com.vmmedico.lab.service.LabReportService;
import com.vmmedico.lab.entity.LabReport;
import com.vmmedico.authentication.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab/bookings")
@CrossOrigin
public class LabAdminBookingController {

    @Autowired private UserService userService;
    @Autowired private LabService labService;
    @Autowired private LabBookingRepository bookingRepo;
    @Autowired private LabReportService reportService;
    @Autowired private EmailService emailService;

    // 🧩 Helper to get current authenticated lab (email OR username from JWT)
    private Lab getAuthenticatedLab() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        String principal = auth.getName(); // value from JWT (could be email or username)
        System.out.println("🔍 Auth principal from JWT: " + principal);

        // Try both email and username
        User user = userService.findByEmail(principal)
                .or(() -> userService.findByUsernameOrEmail(principal))
                .orElseThrow(() -> new RuntimeException("User not found for principal: " + principal));

        return labService.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Lab profile not found for user: " + user.getEmail()));
    }

    // ✅ List all bookings for this lab
    @GetMapping("/all")
    public ResponseEntity<?> getBookingsForLab() {
        Lab lab = getAuthenticatedLab();
        List<LabBooking> bookings = bookingRepo.findAllByLabId(lab.getId());

        // Convert to DTOs
        List<LabBookingResponseDTO> response = bookings.stream()
                .map(LabBookingResponseDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ✅ Search bookings by email/phone/name
    @GetMapping("/search")
    public ResponseEntity<?> searchBookings(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String name
    ) {
        Lab lab = getAuthenticatedLab();

        List<LabBooking> results;
        if (email != null) {
            results = bookingRepo.findByLabIdAndPatientUserEmailContainingIgnoreCase(lab.getId(), email);
        } else if (phone != null) {
            results = bookingRepo.findByLabIdAndPatientPhoneNumberContaining(lab.getId(), phone);
        } else if (name != null) {
            results = bookingRepo.findByLabIdAndPatientNameContainingIgnoreCase(lab.getId(), name);
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Provide email, phone, or name"));
        }

        return ResponseEntity.ok(Map.of("success", !results.isEmpty(), "results", results));
    }

    // ✅ Mark booking completed
    @PostMapping("/{bookingId}/complete")
    public ResponseEntity<?> markCompleted(@PathVariable Long bookingId) {
        Lab lab = getAuthenticatedLab();

        LabBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getLab().getId().equals(lab.getId())) {
            return ResponseEntity.status(403).body("Not allowed");
        }

        booking.setBookingStatus(LabBooking.BookingStatus.COMPLETED);
        bookingRepo.save(booking);

        return ResponseEntity.ok(Map.of("message", "Booking marked completed"));
    }

    // ✅ Upload PDF report
    @PostMapping("/{bookingId}/upload")
    public ResponseEntity<?> uploadReport(
            @PathVariable Long bookingId,
            @RequestParam("file") MultipartFile file
    ) {
        Lab lab = getAuthenticatedLab();

        LabBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getLab().getId().equals(lab.getId())) {
            return ResponseEntity.status(403).body("Not allowed to upload for this booking");
        }

        try {
            LabReport rep = reportService.uploadReport(bookingId, file, lab.getUser().getUsername());

            // Send email to patient
            String testName = "";
            if (booking.getLabTest() != null && booking.getLabTest().getName() != null) {
                testName = booking.getLabTest().getName();
            }

            String message = "Dear " + booking.getPatient().getName() + ",\n\n" +
                    "Your report" + (!testName.isEmpty() ? " for test " + testName : "") +
                    " on " + booking.getDate() + " is now available. Please login to download.\n\n" +
                    "Regards,\n" +
                    lab.getLabName();

            emailService.sendMail(
                    booking.getPatient().getUser().getEmail(),
                    "Your Lab Report is Ready - VMedico",
                    message
            );
            return ResponseEntity.ok(Map.of("message", "Report uploaded", "reportId", rep.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
