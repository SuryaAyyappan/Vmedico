//package com.vmmedico.vmmedico.service;
//
//import com.vmmedico.vmmedico.entity.Lab;
//import com.vmmedico.vmmedico.repository.LabRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class LabService {
//
//    @Autowired
//    private LabRepository labRepository;
//
//    public Lab saveLab(Lab lab) {
//        return labRepository.save(lab);
//    }
//
//    public Optional<Lab> findById(Long id) {
//        return labRepository.findById(id);
//    }
//
//    public boolean phoneExists(String phoneNumber) {
//        return labRepository.existsByPhoneNumber(phoneNumber);
//    }
//}
package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.Lab;
import com.vmmedico.authentication.repository.LabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LabService {

    @Autowired
    private LabRepository labRepository;

    // Save lab
    public Lab saveLab(Lab lab) {
        return labRepository.save(lab);
    }

    // Check if phone number exists
    public boolean existsByPhoneNumber(String phoneNumber) {
        return labRepository.existsByPhoneNumber(phoneNumber);
    }

    // Find lab by ID
    public Optional<Lab> findById(Long id) {
        return labRepository.findById(id);
    }
}
