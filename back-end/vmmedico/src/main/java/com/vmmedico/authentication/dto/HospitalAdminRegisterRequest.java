//package com.vmmedico.vmmedico.dto;
//
//import lombok.Data;
//
//@Data
//public class HospitalAdminRegisterRequest {
//    private String username;
//    private String password;
//    private String email;
//    private String phoneNumber;
//
//    private String name;
//    private String hospitalName;
//    private String designation;
//}
package com.vmmedico.authentication.dto;

import lombok.Data;

@Data
public class HospitalAdminRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String name;
    private String hospitalName;
    private String designation;
}
