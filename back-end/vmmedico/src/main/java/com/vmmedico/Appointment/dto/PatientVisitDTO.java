package com.vmmedico.Appointment.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientVisitDTO {
    private Long appointmentId;
    private String patientName;
    private String patientEmail;
    private String doctorName;
    private String specialization;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
}
