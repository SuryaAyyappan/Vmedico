package com.vmmedico.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String usernameOrEmail;  // can be either username or email
    private String password;         // plain password from frontend
    private String role;             // role selected from frontend (PATIENT / DOCTOR / LAB / HOSPITAL_ADMIN)
}
