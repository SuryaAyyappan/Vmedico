package com.vmmedico.lab.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabBookingDTO {
    private Long id;
    private String testName;
    private String labName;
    private String patientName;
    private LocalDate date;
    private Boolean reportUploaded;
    private String reportFileName;
}

