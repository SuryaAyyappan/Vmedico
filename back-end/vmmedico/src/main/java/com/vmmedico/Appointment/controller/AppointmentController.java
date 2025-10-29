package com.vmmedico.Appointment.controller;

import com.vmmedico.Appointment.dto.AppointmentRequestDTO;
import com.vmmedico.Appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequestDTO request) {
        return appointmentService.bookAppointment(
                request.getDoctorUserId(),
                request.getPatientUserId(),
                request.getDate(),
                request.getStartTime()
        );
    }
}
