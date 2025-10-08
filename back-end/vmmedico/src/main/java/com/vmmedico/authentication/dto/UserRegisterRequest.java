package com.vmmedico.authentication.dto;

import com.vmmedico.authentication.entity.User;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private String email;
    private User.Role role; // Only PATIENT, DOCTOR, HOSPITAL_ADMIN, LAB
}
