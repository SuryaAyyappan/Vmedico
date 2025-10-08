//package com.vmmedico.vmmedico.dto;
//
//import lombok.Data;
//
//@Data
//public class LoginRequest {
//    private String usernameOrEmail; // can be username or email
//    private String password;
//}
package com.vmmedico.authentication.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
