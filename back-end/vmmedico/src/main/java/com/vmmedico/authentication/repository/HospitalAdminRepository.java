package com.vmmedico.authentication.repository;

import com.vmmedico.authentication.entity.HospitalAdmin;
import com.vmmedico.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HospitalAdminRepository extends JpaRepository<HospitalAdmin, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<HospitalAdmin> findByUser(User user);
}
