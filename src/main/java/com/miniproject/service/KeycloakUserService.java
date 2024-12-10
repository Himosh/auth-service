package com.miniproject.service;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakUserService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakUserService(
            @Value("${keycloak.auth-server-url}") String serverUrl,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.username}") String adminUsername,
            @Value("${keycloak.password}") String adminPassword) {

        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("rishad")
                .clientId(clientId)
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    public String createUser(String username, String email, String password, String role, Map<String, List<String>> attributes) {
        // Create credentials
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(password);
        credentials.setTemporary(false);

        // Create user
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(credentials));
        user.setAttributes(attributes);

        // Add user to Keycloak
        Response response = keycloak.realm(realm).users().create(user);

        if (response.getStatus() == 201) {
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            // Assign role
            assignRoleToUser(userId, role);

            return userId;
        } else {
            throw new RuntimeException("Failed to create user: " + response.getStatus());
        }
    }

    private void assignRoleToUser(String userId, String roleName) {
        var roles = keycloak.realm(realm).roles().get(roleName).toRepresentation();
        var userResource = keycloak.realm(realm).users().get(userId);
        userResource.roles().realmLevel().add(Collections.singletonList(roles));
    }

    public void updateUser(
            String keycloakUserId,
            String username,
            String email,
//            Map<String, List<String>> attributes,
            String newPassword,
            String newRole) {
        // Update user details
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
//        user.setAttributes(attributes);

        var userResource = keycloak.realm(realm).users().get(keycloakUserId);
        userResource.update(user);

        // Update password
        if (newPassword != null && !newPassword.isEmpty()) {
            CredentialRepresentation credentials = new CredentialRepresentation();
            credentials.setType(CredentialRepresentation.PASSWORD);
            credentials.setValue(newPassword);
            credentials.setTemporary(false);

            userResource.resetPassword(credentials);
        }

        // Update role
        if (newRole != null && !newRole.isEmpty()) {
            // Remove existing roles
            var existingRoles = userResource.roles().realmLevel().listAll();
            userResource.roles().realmLevel().remove(existingRoles);

            // Add the new role
            var newRoleRepresentation = keycloak.realm(realm).roles().get(newRole).toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(newRoleRepresentation));
        }
    }


    public void deleteUser(String keycloakUserId) {
        keycloak.realm(realm).users().get(keycloakUserId).remove();
    }

}

