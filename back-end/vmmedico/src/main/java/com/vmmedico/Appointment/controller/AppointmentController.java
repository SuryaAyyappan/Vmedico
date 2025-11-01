package com.vmmedico.Appointment.controller;

import com.vmmedico.Appointment.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody Map<String, String> request) {
        Long doctorId = Long.valueOf(request.get("doctorId"));
        Long patientId = Long.valueOf(request.get("patientId"));
        LocalDate date = LocalDate.parse(request.get("date"));
        LocalTime startTime = LocalTime.parse(request.get("startTime"));

        return appointmentService.bookAppointment(doctorId, patientId, date, startTime);
    }
}
