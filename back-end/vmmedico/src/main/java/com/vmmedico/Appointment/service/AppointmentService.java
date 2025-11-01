package com.vmmedico.Appointment.service;

import com.vmmedico.Appointment.entity.Appointment;
import com.vmmedico.Appointment.repo.AppointmentRepository;
import com.vmmedico.authentication.entity.Doctor;
import com.vmmedico.authentication.entity.Patient;
import com.vmmedico.authentication.entity.User;
import com.vmmedico.authentication.repository.DoctorRepository;
import com.vmmedico.authentication.repository.PatientRepository;
import com.vmmedico.common.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final EmailService emailService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository,
                              EmailService emailService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.emailService = emailService;
    }

    @Transactional
    public ResponseEntity<?> bookAppointment(Long doctorId, Long patientId, LocalDate date, LocalTime startTime) {
        Doctor doctor = doctorRepository.findByUserId(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findByUserId(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User user = patient.getUser();

        LocalTime endTime = startTime.plusHours(1);

        // Prevent booking in past
        if (date.isBefore(LocalDate.now()) ||
                (date.isEqual(LocalDate.now()) && startTime.isBefore(LocalTime.now()))) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Cannot book appointments in the past"
            ));
        }

        // Validate allowed time ranges
        LocalTime[] validSlots = {
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0)
        };

        boolean isValidSlot = false;
        for (LocalTime slot : validSlots) {
            if (slot.equals(startTime)) {
                isValidSlot = true;
                break;
            }
        }

        if (!isValidSlot) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Invalid appointment time. Valid slots are 9-10,10-11,11-12,12-1,2-3,3-4,4-5,5-6"
            ));
        }

        // Prevent double booking
        boolean exists = appointmentRepository.existsByDoctorIdAndDateAndStartTime(doctorId, date, startTime);
        if (exists) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "This slot is already booked"
            ));
        }

        // Save appointment
        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .status(Appointment.Status.CONFIRMED)
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        // Send email to patient
        emailService.sendAppointmentConfirmation(
                user.getEmail(),
                patient.getName(),
                doctor.getName(),
                date,
                startTime,
                endTime
        );

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Appointment booked successfully",
                "appointmentId", saved.getId(),
                "doctorId", doctor.getId(),
                "patientId", patient.getId(),
                "startTime", startTime.toString(),
                "endTime", endTime.toString(),
                "date", date.toString()
        ));
    }
}
