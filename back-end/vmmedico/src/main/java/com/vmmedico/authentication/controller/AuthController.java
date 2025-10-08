//package com.vmmedico.vmmedico.controller;
//
//import com.vmmedico.vmmedico.entity.User;
//import com.vmmedico.vmmedico.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // -------------------- Register user --------------------
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
//        if (isEmpty(request.getUsername())) return badRequest("Username is required");
//        if (isEmpty(request.getPassword())) return badRequest("Password is required");
//        if (isEmpty(request.getEmail())) return badRequest("Email is required");
//        if (isEmpty(request.getRole())) return badRequest("Role is required");
//        if (isEmpty(request.getPhoneNumber()) || !request.getPhoneNumber().matches("\\d{10}"))
//            return badRequest("Phone number must be 10 digits");
//        if (!request.getEmail().endsWith("@gmail.com")) return badRequest("Email must end with @gmail.com");
//
//        if (userRepository.existsByUsername(request.getUsername())) return badRequest("Username already exists");
//        if (userRepository.existsByEmail(request.getEmail())) return badRequest("Email already exists");
//        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) return badRequest("Phone number already exists");
//
//        User.Role userRole;
//        try {
//            userRole = User.Role.valueOf(request.getRole().toUpperCase());
//        } catch (Exception e) {
//            return badRequest("Invalid role. Allowed roles: PATIENT, DOCTOR, LAB, HOSPITAL_ADMIN");
//        }
//
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setEmail(request.getEmail());
//        user.setPhoneNumber(request.getPhoneNumber());
//        user.setRole(userRole);
//
//        userRepository.save(user);
//        return ResponseEntity.ok(new SuccessResponse("User registered successfully", user));
//    }
//
//    // -------------------- Login --------------------
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
//        if (user == null) return badRequest("Invalid username or password");
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
//            return badRequest("Invalid username or password");
//
//        return ResponseEntity.ok(new SuccessResponse("Login successful", user));
//    }
//
//    // -------------------- Helpers --------------------
//    private boolean isEmpty(String str) { return str == null || str.trim().isEmpty(); }
//    private ResponseEntity<ErrorResponse> badRequest(String message) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
//    }
//
//    // -------------------- DTOs --------------------
//    public static class RegisterRequest {
//        private String username;
//        private String password;
//        private String email;
//        private String role;
//        private String phoneNumber;
//
//        public String getUsername() { return username; }
//        public void setUsername(String username) { this.username = username; }
//        public String getPassword() { return password; }
//        public void setPassword(String password) { this.password = password; }
//        public String getEmail() { return email; }
//        public void setEmail(String email) { this.email = email; }
//        public String getRole() { return role; }
//        public void setRole(String role) { this.role = role; }
//        public String getPhoneNumber() { return phoneNumber; }
//        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
//    }
//
//    public static class LoginRequest {
//        private String username;
//        private String password;
//        public String getUsername() { return username; }
//        public void setUsername(String username) { this.username = username; }
//        public String getPassword() { return password; }
//        public void setPassword(String password) { this.password = password; }
//    }
//
//    public static class SuccessResponse {
//        private String message;
//        private Object data;
//        public SuccessResponse(String message, Object data) { this.message = message; this.data = data; }
//        public String getMessage() { return message; }
//        public Object getData() { return data; }
//    }
//
//    public static class ErrorResponse {
//        private String message;
//        public ErrorResponse(String message) { this.message = message; }
//        public String getMessage() { return message; }
//
//
//    }
//}
//package com.vmmedico.vmmedico.controller;
//
//import com.vmmedico.vmmedico.entity.User;
//import com.vmmedico.vmmedico.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private UserService userService;
//
//    // ✅ Login by username or email
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String usernameOrEmail,
//                                   @RequestParam String password) {
//        Optional<User> userOpt = userService.findByUsernameOrEmail(usernameOrEmail);
//
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            if (user.getPassword().equals(password)) {
//                return ResponseEntity.ok("Login successful as " + user.getRole());
//            }
//            return ResponseEntity.badRequest().body("Invalid password");
//        }
//
//        return ResponseEntity.badRequest().body("User not found");
//    }
//
//    // ✅ Forgot password
//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
//        Optional<User> userOpt = userService.findByEmail(email);
//
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            userService.sendPasswordByEmail(email, user.getPassword());
//            return ResponseEntity.ok("Password sent to your email");
//        }
//        return ResponseEntity.badRequest().body("Email not registered");
//    }
//}

//package com.vmmedico.vmmedico.controller;
//
//import com.vmmedico.vmmedico.dto.LoginRequest;
//import com.vmmedico.vmmedico.dto.LoginResponse;
//import com.vmmedico.vmmedico.entity.User;
//import com.vmmedico.vmmedico.repository.UserRepository;
//import com.vmmedico.vmmedico.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired private UserRepository userRepository;
//    @Autowired private UserService userService;
//    @Autowired private PasswordEncoder passwordEncoder;
//
//    // -------------------- REGISTER --------------------
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
//        if (isEmpty(request.getUsername())) return badRequest("Username is required");
//        if (isEmpty(request.getPassword())) return badRequest("Password is required");
//        if (isEmpty(request.getEmail())) return badRequest("Email is required");
//        if (isEmpty(request.getPhoneNumber())) return badRequest("Phone number is required");
//        if (isEmpty(request.getRole())) return badRequest("Role is required");
//
//        // Validate email and phone
//        if (!request.getEmail().contains("@")) return badRequest("Invalid email format");
//        if (!request.getPhoneNumber().matches("\\d{10}")) return badRequest("Phone number must be 10 digits");
//
//        // Check duplicates
//        if (userRepository.existsByUsername(request.getUsername())) return badRequest("Username already exists");
//        if (userRepository.existsByEmail(request.getEmail())) return badRequest("Email already exists");
//        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) return badRequest("Phone number already exists");
//
//        User.Role role;
//        try {
//            role = User.Role.valueOf(request.getRole().toUpperCase());
//        } catch (Exception e) {
//            return badRequest("Invalid role. Allowed roles: PATIENT, DOCTOR, LAB, HOSPITAL_ADMIN");
//        }
//
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setEmail(request.getEmail());
//        user.setPhoneNumber(request.getPhoneNumber());
//        user.setRole(role);
//
//        userRepository.save(user);
//        return ResponseEntity.ok(new SuccessResponse("User registered successfully", user));
//    }
//
//    // -------------------- LOGIN --------------------
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        Optional<User> userOpt = userService.findByUsernameOrEmail(request.getUsername());
//        if (userOpt.isEmpty()) return badRequest("Invalid username/email or password");
//
//        User user = userOpt.get();
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            return badRequest("Invalid username/email or password");
//        }
//
//        LoginResponse response = new LoginResponse();
//        response.setMessage("Login successful");
//        response.setUsername(user.getUsername());
//        response.setRole(user.getRole().name());
//
//        return ResponseEntity.ok(response);
//    }
//
//    // -------------------- FORGOT PASSWORD --------------------
//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
//        Optional<User> userOpt = userService.findByEmail(email);
//        if (userOpt.isEmpty()) {
//            return badRequest("No account found with this email");
//        }
//
//        User user = userOpt.get();
//
//        // Generate temporary password, save & send email
//        userService.sendTemporaryPassword(user);
//
//        return ResponseEntity.ok(new SuccessResponse("Temporary password sent to your email", null));
//    }
//
//    // -------------------- Helpers --------------------
//    private boolean isEmpty(String str) { return str == null || str.trim().isEmpty(); }
//
//    private ResponseEntity<ErrorResponse> badRequest(String message) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
//    }
//
//    // -------------------- DTOs --------------------
//    public static class RegisterRequest {
//        private String username;
//        private String password;
//        private String email;
//        private String phoneNumber;
//        private String role;
//
//        public String getUsername() { return username; }
//        public void setUsername(String username) { this.username = username; }
//        public String getPassword() { return password; }
//        public void setPassword(String password) { this.password = password; }
//        public String getEmail() { return email; }
//        public void setEmail(String email) { this.email = email; }
//        public String getPhoneNumber() { return phoneNumber; }
//        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
//        public String getRole() { return role; }
//        public void setRole(String role) { this.role = role; }
//    }
//
//    public static class SuccessResponse {
//        private String message;
//        private Object data;
//        public SuccessResponse(String message, Object data) { this.message = message; this.data = data; }
//        public String getMessage() { return message; }
//        public Object getData() { return data; }
//    }
//
//    public static class ErrorResponse {
//        private String message;
//        public ErrorResponse(String message) { this.message = message; }
//        public String getMessage() { return message; }
//    }
//}
