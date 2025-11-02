package com.vmmedico.authentication.repository;

import com.vmmedico.authentication.entity.HospitalAdmin;
import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabRepository extends JpaRepository<Lab, Long> {

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByLicenseNumber(String licenseNumber);

    Optional<Lab> findByUser(User user);

    // ✅ Find by linked user ID (for authentication-based lookup)
    @Query("SELECT l FROM Lab l WHERE l.user.id = :userId")
    Optional<Lab> findByUserId(@Param("userId") Long userId);
}

