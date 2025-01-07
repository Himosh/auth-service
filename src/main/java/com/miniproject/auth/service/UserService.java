package com.miniproject.auth.service;

import com.miniproject.auth.model.User;
import com.miniproject.auth.model.dto.UserCreateDTO;
import com.miniproject.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Default password
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin user created with username: admin and password: admin123");
        } else {
            System.out.println("Admin user already exists.");
        }
    }

    public User signUp(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    public User signIn(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("Invalid username or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return user;
    }

    public User createDataSteward(String adminUsername, String username, String password) {
        User admin = userRepository.findByUsername(adminUsername).orElseThrow(() ->
                new IllegalArgumentException("Invalid admin credentials"));
        if (admin.getRole() != User.Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can create data stewards.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.Role.DATA_STEWARD);
        return userRepository.save(user);
    }

    public User createAdmin(String adminUsername, String username, String password) {
        User admin = userRepository.findByUsername(adminUsername).orElseThrow(() ->
                new IllegalArgumentException("Invalid admin credentials"));
        if (admin.getRole() != User.Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can create admins.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.Role.ADMIN);
        return userRepository.save(user);
    }

    public User createUser(UserCreateDTO userCreateDTO, String adminUsername) {
        if ("USER".equalsIgnoreCase(userCreateDTO.getRole())) {
            return signUp(userCreateDTO.getUsername(), userCreateDTO.getPassword());
        }

        // For DATA_STEWARD and ADMIN roles, validate admin credentials
        User admin = userRepository.findByUsername(adminUsername).orElseThrow(() ->
                new IllegalArgumentException("Invalid admin credentials"));
        if (admin.getRole() != User.Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can create data stewards or admins.");
        }

        if (userRepository.findByUsername(userCreateDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }

        // Create the appropriate role
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        if ("DATA_STEWARD".equalsIgnoreCase(userCreateDTO.getRole())) {
            user.setRole(User.Role.DATA_STEWARD);
        } else if ("ADMIN".equalsIgnoreCase(userCreateDTO.getRole())) {
            user.setRole(User.Role.ADMIN);
        } else {
            throw new IllegalArgumentException("Invalid role provided!");
        }

        return userRepository.save(user);
    }

    }

