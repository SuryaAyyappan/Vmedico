package com.vmmedico.Appointment.dto;

import com.vmmedico.Appointment.entity.Appointment;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Appointment.Status status;
}
