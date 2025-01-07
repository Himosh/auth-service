//package com.miniproject.controller;
//
//import com.miniproject.model.User;
//import com.miniproject.model.dto.UserDTO;
//import com.miniproject.service.UserService;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/create")
//    @Operation(summary = "Create a new user", description = "Create a new user with the given attributes")
//    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
//        try {
//            log.info("Creating user: {}", userDTO);
//            User user = userService.createUser(userDTO);
//            return ResponseEntity.ok(user);
//        } catch (Exception e) {
//            throw new RuntimeException("Error creating user: " + e.getMessage());
//        }
//    }
//
//    @GetMapping
//    @Operation(summary = "Get all users", description = "Get all users")
//    public ResponseEntity<List<User>> getAllUsers() {
//        try {
//            List<User> users = userService.getAllUsers();
//            return ResponseEntity.ok(users);
//        } catch (Exception e) {
//            throw new RuntimeException("Error retrieving users: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{userId}")
//    @Operation(summary = "Get user by ID", description = "Get user by ID")
//    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
//        try {
//            User user = userService.getUserById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//            return ResponseEntity.ok(user);
//        } catch (Exception e) {
//            throw new RuntimeException("Error retrieving user: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{keycloakUserId}")
//    @Operation(summary = "Get user by ID", description = "Get user by ID")
//    public ResponseEntity<User> getUserById(@PathVariable String keycloakUserId) {
//        try {
//            User user = userService.getUserByKeycloakUserId(keycloakUserId)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//            return ResponseEntity.ok(user);
//        } catch (Exception e) {
//            throw new RuntimeException("Error retrieving user: " + e.getMessage());
//        }
//    }
//
//    @PutMapping("/{userId}")
//    @Operation(summary = "Update user", description = "Update user by ID")
//    public ResponseEntity<User> updateUser(
//            @PathVariable Long userId,
//            @RequestBody User updatedUser) {
//        try {
//            User user = userService.updateUser(userId, updatedUser);
//            return ResponseEntity.ok(user);
//        } catch (Exception e) {
//            throw new RuntimeException("Error updating user: " + e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/{userId}")
//    @Operation(summary = "Delete user", description = "Delete user by ID")
//    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
//        try {
//            userService.deleteUser(userId);
//            return ResponseEntity.ok("User deleted successfully");
//        } catch (Exception e) {
//            throw new RuntimeException("Error deleting user: " + e.getMessage());
//        }
//    }
//}
//
