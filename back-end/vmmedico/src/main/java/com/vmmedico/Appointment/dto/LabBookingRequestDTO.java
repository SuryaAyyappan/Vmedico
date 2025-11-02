package com.vmmedico.Appointment.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class LabBookingRequestDTO {
    private Long labUserId;       // from frontend (Lab Admin User ID)
    private Long patientUserId;   // from frontend (Patient User ID)
    private Long labTestId;       // the test being booked
    private LocalDate date;       // booking date
    private LocalTime startTime;  // start slot time
}
