package com.vmmedico.authentication.controller;

import com.vmmedico.authentication.configuration.JwtTokenUtil;
import com.vmmedico.authentication.dto.*;
import com.vmmedico.authentication.entity.*;
import com.vmmedico.authentication.enums.Role;
import com.vmmedico.authentication.enums.Gender;
import com.vmmedico.authentication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.vmmedico.authentication.service.EmailService;


import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private HospitalAdminService hospitalAdminService;
    @Autowired
    private LabService labService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EmailService emailService;

    // ---------- REGEX VALIDATORS ----------
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[@_!#$%^&*])[A-Za-z\\d@_!#$%^&*]{8,}$");

    private static final Pattern GMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@gmail\\.com$");

    // ---------- SIMULATED VERIFIED REG NUMBERS ----------
    // ---------- HELPER METHODS ----------
    private boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    private ResponseEntity<ErrorResponse> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
    }

    private User createUser(String username, String email, String phoneNumber, String password, Role role) {
        return User.builder()
                .username(username)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
    }

    // ---------- RESPONSE CLASSES ----------
    public static class SuccessResponse {
        private final String message;
        private final Object data;

        public SuccessResponse(String message, Object data) {
            this.message = message;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public Object getData() {
            return data;
        }
    }

    public static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    // ---------- REGISTER PATIENT ----------
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
            User savedUser = userService.saveUser(createUser(
                    request.getUsername(), request.getEmail(),
                    request.getPhoneNumber(), request.getPassword(),
                    Role.PATIENT));

            Patient patient = Patient.builder()
                    .user(savedUser)
                    .name(request.getName())
                    .gender(Gender.valueOf(request.getGender().toUpperCase()))
                    .dob(request.getDob())
                    .address(request.getAddress())
                    .bloodGroup(request.getBloodGroup())
                    .phoneNumber(request.getPhoneNumber())
                    .emergencyContact(request.getEmergencyContact())
                    .build();

            return ResponseEntity.ok(new SuccessResponse("Patient registered successfully", patientService.savePatient(patient)));
        } catch (Exception e) {
            return badRequest("Error: " + e.getMessage());
        }
    }

    // ---------- REGISTER DOCTOR ----------
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
            User savedUser = userService.saveUser(createUser(
                    request.getUsername(), request.getEmail(),
                    request.getPhoneNumber(), request.getPassword(),
                    Role.DOCTOR));

            Doctor doctor = Doctor.builder()
                    .user(savedUser)
                    .name(request.getName())
                    .gender(Gender.valueOf(request.getGender().toUpperCase()))
                    .dob(request.getDob())
                    .specialization(request.getSpecialization())
                    .qualification(request.getQualification())
                    .phoneNumber(request.getPhoneNumber())
                    .build();

            return ResponseEntity.ok(new SuccessResponse("Doctor registered successfully", doctorService.saveDoctor(doctor)));
        } catch (Exception e) {
            return badRequest("Error: " + e.getMessage());
        }
    }

    // ---------- REGISTER LAB ----------
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

        if (!labService.existsByRegistrationNumber(request.getRegistrationNumber()))
            return badRequest("Invalid registration number");

        if (!labService.existsByLicenseNumber(request.getLicenseNumber()))
            return badRequest("Invalid license number");

        try {
            User savedUser = userService.saveUser(createUser(
                    request.getUsername(), request.getEmail(),
                    request.getPhoneNumber(), request.getPassword(),
                    Role.LAB));

            Lab lab = Lab.builder()
                    .user(savedUser)
                    .labName(request.getLabName())
                    .registrationNumber(request.getRegistrationNumber())
                    .licenseNumber(request.getLicenseNumber())
                    .address(request.getAddress())
                    .phoneNumber(request.getPhoneNumber())
                    .build();

            Lab savedLab = labService.saveLab(lab);
            return ResponseEntity.ok(new SuccessResponse("Lab registered successfully", savedLab));
        } catch (Exception e) {
            return badRequest("Error: " + e.getMessage());
        }
    }

    // ---------- REGISTER HOSPITAL ADMIN ----------
    @PostMapping("/register/hospital-admin")
    public ResponseEntity<?> registerHospitalAdmin(@RequestBody HospitalAdminRegisterRequest request) {
        if (!GMAIL_PATTERN.matcher(request.getEmail()).matches())
            return badRequest("Email must be a valid @gmail.com address");
        if (userService.usernameExists(request.getUsername()))
            return badRequest("Username already exists");
        if (userService.emailExists(request.getEmail()))
            return badRequest("Email already exists");
        if (hospitalAdminService.existsByPhoneNumber(request.getPhoneNumber()))
            return badRequest("Phone number already exists");
        if (!isValidPassword(request.getPassword()))
            return badRequest("Password must start with a capital letter, be at least 8 characters, and contain a special character (@,_ etc.)");

        if (!hospitalAdminService.existsByRegistrationNumber(request.getRegistrationNumber()))
            return badRequest("Invalid registration number");

        if (!hospitalAdminService.existsByLicenseNumber(request.getLicenseNumber()))
            return badRequest("Invalid license number");

        try {
            User savedUser = userService.saveUser(createUser(
                    request.getUsername(), request.getEmail(),
                    request.getPhoneNumber(), request.getPassword(),
                    Role.HOSPITAL_ADMIN));

            HospitalAdmin admin = HospitalAdmin.builder()
                    .user(savedUser)
                    .hospitalName(request.getHospitalName())
                    .registrationNumber(request.getRegistrationNumber())
                    .licenseNumber(request.getLicenseNumber())
                    .address(request.getAddress())
                    .phoneNumber(request.getPhoneNumber())
                    .verified(true)
                    .build();

            return ResponseEntity.ok(
                    new SuccessResponse("Hospital Admin registered successfully",
                            hospitalAdminService.saveHospitalAdmin(admin))
            );
        } catch (Exception e) {
            return badRequest("Error: " + e.getMessage());
        }
    }

    @PostMapping("/hospital/add-doctor")
    public ResponseEntity<?> addDoctor(@RequestBody AddDoctorRequest request) {
        try {
            // Check if hospital admin exists
            HospitalAdmin admin = hospitalAdminService.findById(request.getHospitalAdminId())
                    .orElseThrow(() -> new RuntimeException("Hospital admin not found"));

            // Check duplicates
            if (doctorService.existsByPhoneNumber(request.getPhoneNumber()))
                return ResponseEntity.badRequest().body("Phone number already exists");
            if (userService.emailExists(request.getEmail()))
                return ResponseEntity.badRequest().body("Email already exists");

            // Generate random password
            String generatedPassword = generatePassword();

            // ✅ Encode password before saving
            String encodedPassword = passwordEncoder.encode(generatedPassword);

            // Create user for doctor
            User user = userService.saveUser(User.builder()
                    .username(request.getEmail().split("@")[0])
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .password(encodedPassword)  // ✅ FIXED
                    .role(Role.DOCTOR)
                    .build());

            // Create doctor entity
            Doctor doctor = Doctor.builder()
                    .user(user)
                    .name(request.getName())
                    .specialization(request.getSpecialization())
                    .qualification(request.getQualification())
                    .licenseNumber(request.getLicenseNumber())
                    .gender(Gender.valueOf(request.getGender().toUpperCase()))
                    .dob(request.getDob())
                    .phoneNumber(request.getPhoneNumber())
                    .build();

            Doctor savedDoctor = doctorService.saveDoctor(doctor);

            // Send credentials via email
            emailService.sendMail(
                    request.getEmail(),
                    "Welcome to VMedico - Your Doctor Account",
                    "Dear " + request.getName() + ",\n\n" +
                            "You have been added to " + admin.getHospitalName() + ".\n" +
                            "Login using the following credentials:\n\n" +
                            "Username: " + user.getUsername() + "\n" +
                            "Password: " + generatedPassword + "\n\n" +  // ✅ send plain one
                            "Please change your password after first login.\n\n" +
                            "Best Regards,\nVMedico Team"
            );

            return ResponseEntity.ok(
                    new SuccessResponse("Doctor added and credentials sent successfully", savedDoctor)
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding doctor: " + e.getMessage());
        }
    }
    private String generatePassword() {
        int length = 10; // password length
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "@#$%&_!";
        String all = upper + lower + digits + special;

        StringBuilder password = new StringBuilder();
        java.util.Random random = new java.util.Random();

        // Ensure at least one from each category
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));

        // Fill remaining with random mix
        for (int i = 4; i < length; i++) {
            password.append(all.charAt(random.nextInt(all.length())));
        }

        // Shuffle to avoid predictable order
        java.util.List<Character> chars = password.chars()
                .mapToObj(c -> (char) c)
                .collect(java.util.stream.Collectors.toList());
        java.util.Collections.shuffle(chars);
        StringBuilder shuffled = new StringBuilder();
        chars.forEach(shuffled::append);

        return shuffled.toString();
    }



    // ---------- LOGIN ----------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> optionalUser = userService.findByUsernameOrEmail(request.getUsernameOrEmail());
        if (optionalUser.isEmpty())
            return ResponseEntity.badRequest().body(new ErrorResponse("Username/email not registered"));

        User user = optionalUser.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            return ResponseEntity.badRequest().body(new ErrorResponse("Incorrect password"));

        if (!user.getRole().name().equalsIgnoreCase(request.getRole()))
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Role mismatch. Please login through " + user.getRole().name() + " portal."));

        String token = jwtTokenUtil.generateToken(user.getUsername(), user.getRole().name());
        LoginResponse response = new LoginResponse(user.getUsername(), user.getRole().name(), token);

        return ResponseEntity.ok(new SuccessResponse("Login successful", response));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        Optional<User> userOpt = userService.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Email not registered"));
        }

        User user = userOpt.get();

        // Generate temporary password
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        // Encode and update password
        user.setPassword(passwordEncoder.encode(tempPassword));
        userService.saveUser(user);

        // Send temporary password via email
        String subject = "VMedico - Temporary Password";
        String body = "Dear " + user.getUsername() + ",\n\n" +
                "Your temporary password is: " + tempPassword + "\n" +
                "Please log in and change your password immediately.\n\n" +
                "Regards,\nVMedico Team";

        try {
            emailService.sendMail(user.getEmail(), subject, body);
            return ResponseEntity.ok(new SuccessResponse("Temporary password sent to your email", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Failed to send email: " + e.getMessage()));
        }
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        Optional<User> userOpt = userService.findByUsernameOrEmail(request.getUsernameOrEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("User not found"));
        }

        User user = userOpt.get();

        // Verify old password
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Old password is incorrect"));
        }

        // Check if new passwords match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("New passwords do not match"));
        }

        // Validate new password
        if (!isValidPassword(request.getNewPassword())) {
            return ResponseEntity.badRequest().body(new ErrorResponse(
                    "Password must start with a capital letter, be at least 8 characters, and contain a special character (@,_ etc.)"));
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.saveUser(user);

        return ResponseEntity.ok(
                new SuccessResponse("Password changed successfully for " + user.getUsername(), null)
        );

    }
}

