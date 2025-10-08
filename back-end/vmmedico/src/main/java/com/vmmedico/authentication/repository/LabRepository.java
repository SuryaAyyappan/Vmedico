package com.vmmedico.authentication.repository;

import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LabRepository extends JpaRepository<Lab, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Lab> findByUser(User user);
}
