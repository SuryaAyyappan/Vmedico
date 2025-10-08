//package com.vmmedico.vmmedico.dto;
//
//import lombok.Data;
//
//@Data
//public class PatientRegisterRequest {
//    private String username;
//    private String password;
//    private String email;
//    private String phoneNumber;
//
//    private String name;
//    private String gender;
//    private String dob;
//    private String address;
//    private String bloodGroup;
//    private String emergencyContact;
//}
package com.vmmedico.authentication.dto;

import lombok.Data;

@Data
public class PatientRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String name;
    private String gender;
    private String dob;  // format: yyyy-MM-dd
    private String address;
    private String bloodGroup;
    private String emergencyContact;
}
