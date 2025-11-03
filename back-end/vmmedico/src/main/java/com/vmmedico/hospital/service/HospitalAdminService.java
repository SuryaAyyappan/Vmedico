package com.vmmedico.hospital.service;

import com.vmmedico.Appointment.dto.PatientVisitDTO;
import com.vmmedico.Appointment.entity.Appointment;
import com.vmmedico.Appointment.repo.AppointmentRepository;
import com.vmmedico.Doctor.dto.DoctorDTO;
import com.vmmedico.authentication.entity.Doctor;
import com.vmmedico.authentication.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalAdminService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    // Get list of doctors under this hospital
    public List<DoctorDTO> getDoctorsByHospital(String hospitalName) {
        return doctorRepository.findByHospitalName(hospitalName)
                .stream()
                .map(doctor -> DoctorDTO.builder()
                        .id(doctor.getId())
                        .name(doctor.getName())
                        .specialization(doctor.getSpecialization())
                        .phoneNumber(doctor.getPhoneNumber())
                        .qualification(doctor.getQualification())
                        .dob(doctor.getDob())
                        .consultationFee(doctor.getConsultationFee())
                        .experience(doctor.getExperience())
                        .build())
                .collect(Collectors.toList());
    }

    // Get list of patients who visited any doctor in this hospital
    public List<PatientVisitDTO> getPatientVisitsByHospital(String hospitalName) {
        List<Appointment> appointments = appointmentRepository.findByDoctor_HospitalName(hospitalName);
        return appointments.stream()
                .map(appt -> PatientVisitDTO.builder()
                        .appointmentId(appt.getId())
                        .patientName(appt.getPatient().getName())
                        .patientEmail(appt.getPatient().getUser().getEmail())
                        .doctorName(appt.getDoctor().getName())
                        .specialization(appt.getDoctor().getSpecialization())
                        .date(appt.getDate())
                        .startTime(appt.getStartTime())
                        .endTime(appt.getEndTime())
                        .status(appt.getStatus().name())
                        .build())
                .collect(Collectors.toList());
    }
}
