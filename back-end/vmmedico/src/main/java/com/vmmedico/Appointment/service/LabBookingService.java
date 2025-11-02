package com.vmmedico.Appointment.service;

import com.vmmedico.Appointment.entity.LabBooking;
import com.vmmedico.Appointment.repo.LabBookingRepository;
import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.entity.Patient;
import com.vmmedico.authentication.entity.User;
import com.vmmedico.authentication.repository.LabRepository;
import com.vmmedico.authentication.repository.PatientRepository;
import com.vmmedico.authentication.repository.UserRepository;
import com.vmmedico.authentication.service.EmailService;
import com.vmmedico.lab.entity.LabTest;
import com.vmmedico.lab.repo.LabTestRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Service
public class LabBookingService {

    private final LabBookingRepository labBookingRepository;
    private final LabRepository labRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final LabTestRepository labTestRepository;

    public LabBookingService(LabBookingRepository labBookingRepository,
                             LabRepository labRepository,
                             PatientRepository patientRepository,
                             UserRepository userRepository,
                             EmailService emailService,
                             LabTestRepository labTestRepository) {   // ✅ added here
        this.labBookingRepository = labBookingRepository;
        this.labRepository = labRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.labTestRepository = labTestRepository;   // ✅ initialize properly
    }

    @Transactional
    public ResponseEntity<?> bookLabTest(Long labUserId, Long patientUserId,
                                         Long labTestId, LocalDate date, LocalTime startTime) {

        Lab lab = labRepository.findByUserId(labUserId)
                .orElseThrow(() -> new RuntimeException("Lab not found"));

        Patient patient = patientRepository.findByUserId(patientUserId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User user = patient.getUser();

        LabTest labTest = labTestRepository.findById(labTestId)
                .orElseThrow(() -> new RuntimeException("Lab test not found"));

        LocalTime endTime = startTime.plusHours(1);

        boolean exists = labBookingRepository.existsByLabIdAndDateAndStartTime(lab.getId(), date, startTime);
        if (exists) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "This slot is already booked"
            ));
        }

        // ✅ FIX: Set testName to avoid NULL constraint issue
        LabBooking booking = LabBooking.builder()
                .lab(lab)
                .patient(patient)
                .labTest(labTest)
                .testName(labTest.getName())  // ✅ Now never null
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .bookingStatus(LabBooking.BookingStatus.CONFIRMED)
                .build();

        LabBooking saved = labBookingRepository.save(booking);

        // Send confirmation email
        emailService.sendMail(
                user.getEmail(),
                "Lab Test Booking Confirmation - VMedico",
                "Dear " + patient.getName() + ",\n\n" +
                        "Your lab test '" + labTest.getName() + "' has been successfully booked.\n\n" +
                        "Lab: " + lab.getLabName() + "\n" +
                        "Date: " + date + "\n" +
                        "Time: " + startTime + " - " + endTime + "\n\n" +
                        "Thank you for choosing VMedico.\n\n" +
                        "Regards,\nVMedico Team"
        );

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Lab test booked successfully",
                "bookingId", saved.getId(),
                "labId", lab.getId(),
                "patientId", patient.getId(),
                "testName", labTest.getName(),
                "date", date.toString(),
                "startTime", startTime.toString(),
                "endTime", endTime.toString()
        ));
    }

}
