package com.vmmedico.lab.repo;

import com.vmmedico.lab.entity.LabReport;
import com.vmmedico.Appointment.entity.LabBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabReportRepository extends JpaRepository<LabReport, Long> {
    Optional<LabReport> findByBooking(LabBooking booking);
}
