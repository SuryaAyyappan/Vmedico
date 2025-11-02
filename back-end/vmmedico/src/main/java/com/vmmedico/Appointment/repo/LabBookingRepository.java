package com.vmmedico.Appointment.repo;

import com.vmmedico.Appointment.entity.LabBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface LabBookingRepository extends JpaRepository<LabBooking, Long> {
    boolean existsByLabIdAndDateAndStartTime(Long labId, LocalDate date, LocalTime startTime);
    List<LabBooking> findAllByLabId(Long labId);
    List<com.vmmedico.Appointment.entity.LabBooking> findByLabIdAndDate(Long labId, LocalDate date);
    List<com.vmmedico.Appointment.entity.LabBooking> findByLabIdAndPatientPhoneNumberContaining(Long labId, String phone);
    List<com.vmmedico.Appointment.entity.LabBooking> findByLabIdAndPatientUserEmailContainingIgnoreCase(Long labId, String email);

    List<LabBooking> findByLabIdAndPatientNameContainingIgnoreCase(Long id, String name);

    List<LabBooking> findAllByPatientUserIdAndReportUploadedTrue(Long userId);
}
