package com.vmmedico.lab.dto;

import lombok.*;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LabReportDTO {
    private Long id;
    private Long bookingId;
    private String fileName;
    private Instant uploadedAt;
    private String uploadedBy;
}
