package com.vmmedico.Appointment.service;

import com.vmmedico.Appointment.dto.PrescriptionRequestDTO;
import com.vmmedico.Appointment.entity.Appointment;
import com.vmmedico.Appointment.entity.Prescription;
import com.vmmedico.Appointment.repo.PrescriptionRepository;
import com.vmmedico.prescription.util.PdfGenerator;
import com.vmmedico.authentication.entity.Doctor;
import com.vmmedico.authentication.entity.Patient;
import com.vmmedico.authentication.entity.User;
import com.vmmedico.Appointment.repo.AppointmentRepository;
import com.vmmedico.authentication.repository.DoctorRepository;
import com.vmmedico.authentication.repository.PatientRepository;
import com.vmmedico.common.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PrescriptionService {

    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final EmailService emailService;

    // configure where to store prescriptions
    private final String prescriptionBaseDir = "prescriptions";

    public PrescriptionService(AppointmentRepository appointmentRepository,
                               PrescriptionRepository prescriptionRepository,
                               DoctorRepository doctorRepository,
                               PatientRepository patientRepository,
                               EmailService emailService) {
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.emailService = emailService;

        // ensure dir exists
        try {
            File dir = new File(prescriptionBaseDir);
            if (!dir.exists()) dir.mkdirs();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create prescriptions directory", ex);
        }
    }

    @Transactional
    public Prescription generateAndSendPrescription(PrescriptionRequestDTO req) throws Exception {
        Long appointmentId = req.getAppointmentId();
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // validate doctor matches appointment (optional)
        if (req.getDoctorId() != null && !req.getDoctorId().equals(appointment.getDoctor().getId())) {
            throw new RuntimeException("Doctor mismatch for appointment");
        }

        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();
        User patientUser = patient.getUser();
        if (patientUser == null || patientUser.getEmail() == null) {
            throw new RuntimeException("Patient email not found");
        }

        // create PDF
        String hospitalName = req.getHospitalName();
        List<Map<String, Object>> tablets = req.getTablets();
        byte[] pdfBytes = PdfGenerator.generatePrescriptionPdf(
                hospitalName,
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getQualification(),
                doctor.getPhoneNumber(),
                tablets
        );

        // write to disk
        String filename = "presc-" + appointmentId + "-" + UUID.randomUUID() + ".pdf";
        String filePath = prescriptionBaseDir + File.separator + filename;
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfBytes);
        }

        // persist Prescription entity
        Prescription p = Prescription.builder()
                .appointment(appointment)
                .filePath(filePath)
                .fileName(filename)
                .createdAt(LocalDateTime.now())
                .build();
        Prescription saved = prescriptionRepository.save(p);

        // mark appointment completed
        appointment.setStatus(Appointment.Status.COMPLETED);
        appointmentRepository.save(appointment);

        // send email with attachment
        LocalTime startTime = appointment.getStartTime();
        emailService.sendPrescriptionEmail(
                patientUser.getEmail(),
                patientUser.getUsername() != null ? patientUser.getUsername() : (patient.getName() != null ? patient.getName() : "Patient"),
                doctor.getName(),
                appointment.getDate(),
                startTime,
                filename,
                pdfBytes
        );

        return saved;
    }
}
