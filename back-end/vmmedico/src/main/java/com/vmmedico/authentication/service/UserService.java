package com.vmmedico.authentication.service;

import com.vmmedico.authentication.entity.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);

    User saveUser(User user);

    boolean usernameExists(String username);

    boolean emailExists(String email);

    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
    Optional<User> findById(Long id);
}
