package com.vmmedico.lab.repo;

import com.vmmedico.lab.entity.LabTest;
import com.vmmedico.authentication.entity.Lab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LabTestRepository extends JpaRepository<LabTest, Long> {
    List<LabTest> findByLabAndActiveTrue(Lab lab);
    Optional<LabTest> findByLabAndTestCode(Lab lab, String testCode);
    Optional<LabTest> findByIdAndLabId(Long id, Long labId);
}
