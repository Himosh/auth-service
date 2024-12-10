package com.miniproject.service;

import com.miniproject.model.User;
import com.miniproject.model.dto.UserDTO;
import com.miniproject.model.enums.Role;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    User createUser(UserDTO userDTO);
    User updateUser(Long userId, User updatedUser);
    void deleteUser(Long userId);
    Optional<User> getUserById(Long userId);
    List<User> getAllUsers();
}
