package com.vmmedico.Appointment.repo;

import com.vmmedico.Appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Long doctorId,
            LocalDate date,
            LocalTime endTime,
            LocalTime startTime
    );
}
