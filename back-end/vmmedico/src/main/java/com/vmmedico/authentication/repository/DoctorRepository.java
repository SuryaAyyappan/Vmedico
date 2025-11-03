
package com.vmmedico.authentication.repository;

import com.vmmedico.authentication.entity.Doctor;
import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByLicenseNumber(String licenseNumber);

    Optional<Doctor> findByUser(User user);

    Optional<Doctor> findByUserId(Long userId);
    List<Doctor> findByHospitalName(String hospitalName);


}

