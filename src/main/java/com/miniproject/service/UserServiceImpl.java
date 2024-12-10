package com.miniproject.service;

import com.miniproject.model.dto.UserDTO;
import com.miniproject.model.enums.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.miniproject.model.User;
import com.miniproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeycloakUserService keycloakUserService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public User createUser(UserDTO userDTO, Map<String, List<String>> attributes) {

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);

        String keycloakUserId = keycloakUserService.createUser(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().toString(),
                attributes
        );

        savedUser.setKeycloakUserId(keycloakUserId);
        return userRepository.save(savedUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByKeycloakUserId(String keycloakUserId) {
        return userRepository.findByKeycloakUserId(keycloakUserId);
    }

    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId).map(existingUser -> {
            // Update local user details
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            User savedUser = userRepository.save(existingUser);

            // Update user in Keycloak
            keycloakUserService.updateUser(
                    existingUser.getKeycloakUserId(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getPassword(),
                    updatedUser.getRole().toString()
            );

            return savedUser;
        }).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            // Delete user from Keycloak
            keycloakUserService.deleteUser(user.getKeycloakUserId());

            // Delete user from local database
            userRepository.delete(user);
        });
    }
}
