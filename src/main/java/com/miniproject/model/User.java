package com.miniproject.model;

import com.miniproject.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String keycloakUserId;
    @Column(nullable = false)
    private String username;
    private String password;
    private String email;
    private String role;
}
