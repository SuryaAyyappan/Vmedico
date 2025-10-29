package com.vmmedico.authentication.repository;

import com.vmmedico.authentication.entity.Doctor;
import com.vmmedico.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Doctor> findByUser(User user);
    List<Doctor> findBySpecializationIgnoreCase(String specialization);
    Optional<Doctor> findByUserId(Long userId);

}
