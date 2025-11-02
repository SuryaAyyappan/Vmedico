package com.vmmedico.lab.service;

import com.vmmedico.lab.entity.LabTest;
import com.vmmedico.lab.repo.LabTestRepository;
import com.vmmedico.authentication.entity.Lab;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabTestService {

    private final LabTestRepository repo;

    public LabTestService(LabTestRepository repo) {
        this.repo = repo;
    }

    public LabTest createTest(Lab lab, LabTest test) {
        test.setLab(lab);
        return repo.save(test);
    }

    public LabTest updateTest(Long id, Double price, String description, Boolean active) {
        LabTest t = repo.findById(id).orElseThrow(() -> new RuntimeException("Test not found"));
        if (price != null) t.setPrice(price);
        if (description != null) t.setDescription(description);
        if (active != null) t.setActive(active);
        return repo.save(t);
    }

    public List<LabTest> listActiveTests(Lab lab) {
        return repo.findByLabAndActiveTrue(lab);
    }

    public LabTest findByIdAndLab(Long id, Long labId) {
        return repo.findByIdAndLabId(id, labId).orElseThrow(() -> new RuntimeException("Test not found or not owned by lab"));
    }
}
