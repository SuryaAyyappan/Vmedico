package com.vmmedico.authentication.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String usernameOrEmail;  // Username or email to identify the user
    private String oldPassword;       // Current password
    private String newPassword;       // New password
    private String confirmPassword;   // Confirm new password
}

