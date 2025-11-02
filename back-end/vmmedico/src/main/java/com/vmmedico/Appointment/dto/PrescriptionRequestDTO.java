package com.vmmedico.Appointment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PrescriptionRequestDTO {
    private Long appointmentId;
    private Long doctorId;    // optional (we validate against appointment)
    // tablets: list of objects { tabletName: String, morning: boolean, afternoon: boolean, night: boolean, notes: String (opt) }
    private List<Map<String, Object>> tablets;

    private String hospitalName; // optional - frontend can pass or leave null
}
