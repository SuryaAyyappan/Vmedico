package com.vmmedico.Appointment.dto;

import com.vmmedico.Appointment.entity.LabBooking;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class LabBookingResponseDTO {
    private Long id;
    private String testName;
    private String patientName;
    private String patientEmail;
    private String labName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String bookingStatus;
    private Boolean reportUploaded;
    private String reportFileName;

    public static LabBookingResponseDTO fromEntity(LabBooking b) {
        return LabBookingResponseDTO.builder()
                .id(b.getId())
                .testName(
                        b.getLabTest() != null && b.getLabTest().getName() != null
                                ? b.getLabTest().getName()
                                : b.getTestName()
                )
                .patientName(
                        b.getPatient() != null ? b.getPatient().getName() : null
                )
                .patientEmail(
                        (b.getPatient() != null && b.getPatient().getUser() != null)
                                ? b.getPatient().getUser().getEmail()
                                : null
                )
                .labName(
                        b.getLab() != null ? b.getLab().getLabName() : null
                )
                .date(b.getDate())
                .startTime(b.getStartTime())
                .endTime(b.getEndTime())
                .bookingStatus(
                        b.getBookingStatus() != null ? b.getBookingStatus().name() : "CONFIRMED"
                )
                .reportUploaded(b.getReportUploaded())
                .reportFileName(b.getReportFileName())
                .build();
    }
}
