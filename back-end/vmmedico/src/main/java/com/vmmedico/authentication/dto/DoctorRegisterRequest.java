//package com.vmmedico.vmmedico.dto;
//
//import lombok.Data;
//
//@Data
//public class DoctorRegisterRequest {
//    private String username;
//    private String password;
//    private String email;
//    private String phoneNumber;
//
//    private String name;
//    private String gender;
//    private String dob;
//    private String specialization;
//    private String qualification;
//}
package com.vmmedico.authentication.dto;

import lombok.Data;

@Data
public class DoctorRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String name;
    private String gender;
    private String dob; // format: yyyy-MM-dd
    private String specialization;
    private String qualification;
}
