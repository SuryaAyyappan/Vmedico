//package com.vmmedico.vmmedico.service;
//
//import com.vmmedico.vmmedico.entity.HospitalAdmin;
//import com.vmmedico.vmmedico.repository.HospitalAdminRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class HospitalAdminService {
//
//    @Autowired
//    private HospitalAdminRepository hospitalAdminRepository;
//
//    public HospitalAdmin saveHospitalAdmin(HospitalAdmin admin) {
//        return hospitalAdminRepository.save(admin);
//    }
//
//    public Optional<HospitalAdmin> findById(Long id) {
//        return hospitalAdminRepository.findById(id);
//    }
//
//    public boolean phoneExists(String phoneNumber) {
//        return hospitalAdminRepository.existsByPhoneNumber(phoneNumber);
//    }
//}
package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.HospitalAdmin;
import com.vmmedico.authentication.repository.HospitalAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HospitalAdminService {

    @Autowired
    private HospitalAdminRepository hospitalAdminRepository;

    // Save hospital admin
    public HospitalAdmin saveHospitalAdmin(HospitalAdmin admin) {
        return hospitalAdminRepository.save(admin);
    }

    // Check if phone number exists
    public boolean existsByPhoneNumber(String phoneNumber) {
        return hospitalAdminRepository.existsByPhoneNumber(phoneNumber);
    }

    // Find hospital admin by ID
    public Optional<HospitalAdmin> findById(Long id) {
        return hospitalAdminRepository.findById(id);
    }
}
