//package com.vmmedico.vmmedico.service;
//
//import com.vmmedico.vmmedico.entity.User;
//import com.vmmedico.vmmedico.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.regex.Pattern;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // Password validation: starts with capital, min 8 chars, 1 special character
//    private static final Pattern PASSWORD_PATTERN =
//            Pattern.compile("^(?=.*[A-Z])(?=.*[@_!#$%^&*])[A-Za-z\\d@_!#$%^&*]{8,}$");
//
//    public User saveUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
//        return userRepository.findByUsername(usernameOrEmail)
//                .or(() -> userRepository.findByEmail(usernameOrEmail));
//    }
//
//    public boolean usernameExists(String username) {
//        return userRepository.existsByUsername(username);
//    }
//
//    public boolean emailExists(String email) {
//        return userRepository.existsByEmail(email);
//    }
//
//    public boolean isValidPassword(String password) {
//        return PASSWORD_PATTERN.matcher(password).matches();
//    }
//
//    // -------------------- Change Password --------------------
//    public boolean changePassword(String usernameOrEmail, String oldPassword, String newPassword, String confirmPassword) {
//        Optional<User> optionalUser = findByUsernameOrEmail(usernameOrEmail);
//        if (optionalUser.isEmpty()) return false;
//
//        User user = optionalUser.get();
//
//        // Check old password
//        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
//            return false;
//        }
//
//        // Check new password matches confirm password
//        if (!newPassword.equals(confirmPassword)) {
//            return false;
//        }
//
//        // Encode and update password
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//        return true;
//    }
//
//    // -------------------- Validate Login --------------------
//    public LoginResult validateLogin(String usernameOrEmail, String password) {
//        Optional<User> optionalUser = findByUsernameOrEmail(usernameOrEmail);
//        if (optionalUser.isEmpty()) return LoginResult.USER_NOT_FOUND;
//
//        User user = optionalUser.get();
//        if (!passwordEncoder.matches(password, user.getPassword())) return LoginResult.WRONG_PASSWORD;
//
//        return LoginResult.SUCCESS;
//    }
//
//    public enum LoginResult {
//        SUCCESS,
//        USER_NOT_FOUND,
//        WRONG_PASSWORD
//    }
//}
package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.User;
import com.vmmedico.authentication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Password must start with a capital letter, min 8 chars, at least 1 special character (@,_ etc.)
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[@_!#$%^&*])[A-Za-z\\d@_!#$%^&*]{8,}$");

    // -------------------- Save User --------------------
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // -------------------- Find by Username or Email --------------------
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail));
    }

    // -------------------- Check existence --------------------
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // -------------------- Password validation --------------------
    public boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    // -------------------- Change Password --------------------
    public boolean changePassword(String usernameOrEmail, String oldPassword, String newPassword, String confirmPassword) {
        Optional<User> optionalUser = findByUsernameOrEmail(usernameOrEmail);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();

        // Check old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) return false;

        // Check new password matches confirm password
        if (!newPassword.equals(confirmPassword)) return false;

        // Encode and update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    // -------------------- Login validation --------------------
    public LoginResult validateLogin(String usernameOrEmail, String password) {
        Optional<User> optionalUser = findByUsernameOrEmail(usernameOrEmail);
        if (optionalUser.isEmpty()) return LoginResult.USER_NOT_FOUND;

        User user = optionalUser.get();
        if (!passwordEncoder.matches(password, user.getPassword())) return LoginResult.WRONG_PASSWORD;

        return LoginResult.SUCCESS;
    }

    // -------------------- Enum for Login Result --------------------
    public enum LoginResult {
        SUCCESS,
        USER_NOT_FOUND,
        WRONG_PASSWORD
    }
}
