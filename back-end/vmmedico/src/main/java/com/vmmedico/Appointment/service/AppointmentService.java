package com.vmmedico.Appointment.service;

import com.vmmedico.Appointment.entity.Appointment;
import com.vmmedico.Appointment.repo.AppointmentRepository;
import com.vmmedico.authentication.entity.Doctor;
import com.vmmedico.authentication.entity.Patient;
import com.vmmedico.authentication.repository.DoctorRepository;
import com.vmmedico.authentication.repository.PatientRepository;
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

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Books an appointment only in predefined 30-minute slots with 5-minute breaks.
     */
    @Transactional
    public ResponseEntity<?> bookAppointment(Long doctorUserId, Long patientUserId,
                                             LocalDate date, LocalTime startTime) {
        Doctor doctor = doctorRepository.findByUserId(doctorUserId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findByUserId(patientUserId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        int appointmentMinutes = 30;
        int breakMinutes = 5;
        LocalTime endTime = startTime.plusMinutes(appointmentMinutes);

        // 🗓️ Date validation
        if (date.isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Cannot book appointment for past dates."
            ));
        }

        // ⏰ Time validation (if same day)
        if (date.isEqual(LocalDate.now()) && startTime.isBefore(LocalTime.now())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Cannot book appointment for past time slots."
            ));
        }

        // 🍱 Lunch break validation
        LocalTime lunchStart = LocalTime.of(12, 20);
        LocalTime lunchEnd = LocalTime.of(13, 10);
        if (!(endTime.isBefore(lunchStart) || startTime.isAfter(lunchEnd))) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Doctor unavailable during lunch (12:20 - 13:10)"
            ));
        }

        // 🔁 Overlap validation
        LocalTime safeStart = startTime.minusMinutes(breakMinutes);
        LocalTime safeEnd = endTime.plusMinutes(breakMinutes);
        boolean isConflict = appointmentRepository
                .existsByDoctorIdAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        doctor.getId(), date, safeEnd, safeStart);

        if (isConflict) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Selected slot overlaps with another appointment or buffer time."
            ));
        }

        // ✅ Save appointment
        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .status(Appointment.Status.CONFIRMED)
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Appointment booked successfully.",
                "appointment", Map.of(
                        "id", saved.getId(),
                        "doctorId", doctor.getId(),
                        "patientId", patient.getId(),
                        "date", date.toString(),
                        "startTime", startTime.toString(),
                        "endTime", endTime.toString(),
                        "status", saved.getStatus().name()
                )
        ));
    }

}
