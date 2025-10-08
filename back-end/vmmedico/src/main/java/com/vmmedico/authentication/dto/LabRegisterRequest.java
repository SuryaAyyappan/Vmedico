//package com.vmmedico.vmmedico.dto;
//
//import lombok.Data;
//
//@Data
//public class LabRegisterRequest {
//    private String username;
//    private String password;
//    private String email;
//    private String phoneNumber;
//
//    private String labName;
//    private String licenseNumber;
//}
package com.vmmedico.authentication.dto;

import lombok.Data;

@Data
public class LabRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String labName;
    private String licenseNumber;
}
