
package com.vmmedico.authentication.controller;

import com.vmmedico.authentication.dto.*;
import com.vmmedico.authentication.entity.*;
import com.vmmedico.authentication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class RegisterController {

    @Autowired private UserService userService;
    @Autowired private PatientService patientService;
    @Autowired private DoctorService doctorService;
    @Autowired private HospitalAdminService hospitalAdminService;
    @Autowired private LabService labService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JavaMailSender mailSender;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[@_!#$%^&*])[A-Za-z\\d@_!#$%^&*]{8,}$");

    private static final Pattern GMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@gmail\\.com$");

    // -------------------- Register Patient --------------------
    @PostMapping("/register/patient")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRegisterRequest request) {
        if (!GMAIL_PATTERN.matcher(request.getEmail()).matches())
            return badRequest("Email must be a valid @gmail.com address");

        if (userService.usernameExists(request.getUsername()))
            return badRequest("Username already exists");

        if (userService.emailExists(request.getEmail()))
            return badRequest("Email already exists");

        if (patientService.existsByPhoneNumber(request.getPhoneNumber()))
            return badRequest("Phone number already exists");

        if (patientService.existsByEmergencyContact(request.getEmergencyContact()))
            return badRequest("Emergency contact already exists");

        if (!isValidPassword(request.getPassword()))
            return badRequest("Password must start with a capital letter, be at least 8 characters, and contain a special character (@,_ etc.)");

        try {
            User user = createUser(request.getUsername(), request.getEmail(), request.getPhoneNumber(),
                    request.getPassword(), User.Role.PATIENT);
            User savedUser = userService.saveUser(user);

            Patient patient = new Patient();
            patient.setUser(savedUser);
            patient.setName(request.getName());
            patient.setGender(Patient.Gender.valueOf(request.getGender().toUpperCase()));
            patient.setDob(request.getDob());
            patient.setAddress(request.getAddress());
            patient.setBloodGroup(request.getBloodGroup());
            patient.setPhoneNumber(request.getPhoneNumber());
            patient.setEmergencyContact(request.getEmergencyContact());

            Patient savedPatient = patientService.savePatient(patient);
            return ResponseEntity.ok(new SuccessResponse("Patient registered successfully", savedPatient));
        } catch (Exception e) {
            return badRequest("Error: " + e.getMessage());
        }
    }

    // -------------------- Register Doctor --------------------
    @PostMapping("/register/doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody DoctorRegisterRequest request) {
        if (!GMAIL_PATTERN.matcher(request.getEmail()).matches())
            return badRequest("Email must be a valid @gmail.com address");

        if (userService.usernameExists(request.getUsername()))
            return badRequest("Username already exists");

        if (userService.emailExists(request.getEmail()))
            return badRequest("Email already exists");

        if (doctorService.existsByPhoneNumber(request.getPhoneNumber()))
            return badRequest("Phone number already exists");

        if (!isValidPassword(request.getPassword()))
            return badRequest("Password must start with a capital letter, be at least 8 characters, and contain a special character (@,_ etc.)");

        try {
            User user = createUser(request.getUsername(), request.getEmail(), request.getPhoneNumber(),
                    request.getPassword(), User.Role.DOCTOR);
            User savedUser = userService.saveUser(user);

            Doctor doctor = new Doctor();
            doctor.setUser(savedUser);
            doctor.setName(request.getName());
            doctor.setGender(Doctor.Gender.valueOf(request.getGender().toUpperCase()));
            doctor.setDob(request.getDob());
            doctor.setSpecialization(request.getSpecialization());
            doctor.setQualification(request.getQualification());
            doctor.setPhoneNumber(request.getPhoneNumber());

            Doctor savedDoctor = doctorService.saveDoctor(doctor);
            return ResponseEntity.ok(new SuccessResponse("Doctor registered successfully", savedDoctor));
        } catch (Exception e) {
            return badRequest("Error: " + e.getMessage());
        }
    }




    // -------------------- Register Lab --------------------
    @PostMapping("/register/lab")
    public ResponseEntity<?> registerLab(@RequestBody LabRegisterRequest request) {
        if (!GMAIL_PATTERN.matcher(request.getEmail()).matches())
            return badRequest("Email must be a valid @gmail.com address");

        if (userService.usernameExists(request.getUsername()))
            return badRequest("Username already exists");

        if (userService.emailExists(request.getEmail()))
            return badRequest("Email already exists");

        if (labService.existsByPhoneNumber(request.getPhoneNumber()))
            return badRequest("Phone number already exists");

        if (!isValidPassword(request.getPassword()))
            return badRequest("Password must start with a capital letter, be at least 8 characters, and contain a special character (@,_ etc.)");

        try {
            User user = createUser(request.getUsername(), request.getEmail(), request.getPhoneNumber(),
                    request.getPassword(), User.Role.LAB);
            User savedUser = userService.saveUser(user);

            Lab lab = new Lab();
            lab.setUser(savedUser);
            lab.setLabName(request.getLabName());
            lab.setLicenseNumber(request.getLicenseNumber());
            lab.setPhoneNumber(request.getPhoneNumber());

            Lab savedLab = labService.saveLab(lab);
            return ResponseEntity.ok(new SuccessResponse("Lab registered successfully", savedLab));
        } catch (Exception e) {
            return badRequest("Error: " + e.getMessage());
        }
    }

    // -------------------- Login --------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var optionalUser = userService.findByUsernameOrEmail(request.getUsernameOrEmail());
        if (optionalUser.isEmpty()) return badRequest("Username/email not registered");

        User user = optionalUser.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            return badRequest("Password is incorrect");

        return ResponseEntity.ok(new LoginResponse("Login successful", user.getUsername(), user.getRole().name()));
    }

    // -------------------- Forgot Password --------------------
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        var optionalUser = userService.findByUsernameOrEmail(request.getEmail());
        if (optionalUser.isEmpty()) return badRequest("Email not found");

        User user = optionalUser.get();
        try {
            String tempPassword = UUID.randomUUID().toString().substring(0, 8);
            user.setPassword(passwordEncoder.encode(tempPassword));
            userService.saveUser(user);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Password Recovery");
            message.setText("Hello " + user.getUsername() + ", your temporary password is: " + tempPassword +
                    "\nPlease login and change your password.");
            message.setFrom("sunilraj200419@gmail.com"); // match spring.mail.username
            mailSender.send(message);

            return ResponseEntity.ok(new SuccessResponse("Temporary password sent to your email", null));
        } catch (Exception e) {
            return badRequest("Failed to send email: " + e.getMessage());
        }
    }

    // -------------------- Change Password --------------------
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        boolean changed = userService.changePassword(
                request.getUsernameOrEmail(),
                request.getOldPassword(),
                request.getNewPassword(),
                request.getConfirmPassword()
        );

        if (!changed) return badRequest("Password change failed. Check old password or confirm password.");

        return ResponseEntity.ok(new SuccessResponse("Password changed successfully", null));
    }

    // -------------------- Helpers --------------------
    private boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    private ResponseEntity<ErrorResponse> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
    }

    private User createUser(String username, String email, String phoneNumber, String password, User.Role role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return user;
    }

    public static class SuccessResponse {
        private String message;
        private Object data;
        public SuccessResponse(String message, Object data) { this.message = message; this.data = data; }
        public String getMessage() { return message; }
        public Object getData() { return data; }
    }

    public static class ErrorResponse {
        private String message;
        public ErrorResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
    }
}
