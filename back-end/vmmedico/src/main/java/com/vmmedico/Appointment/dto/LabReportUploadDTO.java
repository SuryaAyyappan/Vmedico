package com.vmmedico.Appointment.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LabReportUploadDTO {
    private String patientEmailOrPhone;  // lab admin identifies patient
    private Long labTestId;              // optional, used for lookup
    private MultipartFile reportFile;    // uploaded report PDF
}
