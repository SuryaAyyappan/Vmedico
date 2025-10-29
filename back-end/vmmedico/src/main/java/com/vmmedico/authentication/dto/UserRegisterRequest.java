package com.vmmedico.authentication.dto;

import com.vmmedico.authentication.enums.Role;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private String email;
    private Role role; // ✅ Use the shared Role enum from com.vmmedico.authentication.enums
}
