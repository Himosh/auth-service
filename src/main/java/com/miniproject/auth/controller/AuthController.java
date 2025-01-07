package com.miniproject.auth.controller;

import com.miniproject.auth.model.User;
import com.miniproject.auth.model.dto.UserCreateDTO;
import com.miniproject.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public User signUp(@RequestParam String username, @RequestParam String password) {
        return userService.signUp(username, password);
    }

    @PostMapping("/sign-in")
    public User signIn(@RequestParam String username, @RequestParam String password) {
        return userService.signIn(username, password);
    }

    @PostMapping("/create-data-steward")
    public User createDataSteward(
            @RequestParam String adminUsername,
            @RequestParam String username,
            @RequestParam String password) {
        return userService.createDataSteward(adminUsername, username, password);
    }

    @PostMapping("/create-admin")
    public User createAdmin(
            @RequestParam String adminUsername,
            @RequestParam String username,
            @RequestParam String password) {
        return userService.createAdmin(adminUsername, username, password);
    }

    @PostMapping("/create-user")
    public User createUser(
            @RequestParam(required = false) String adminUsername,
           @RequestBody UserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO, adminUsername);
    }
}

