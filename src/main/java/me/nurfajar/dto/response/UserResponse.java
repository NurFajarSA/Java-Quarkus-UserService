package me.nurfajar.dto.response;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.nurfajar.model.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;

    private String email;

    private String username;

    private Role role;

    private LocalDateTime dateCreate = LocalDateTime.now();

    private LocalDateTime dateUpdate = LocalDateTime.now();

    private LocalDateTime lastLogin;

    private int loginAttempt = 0;

    private boolean isDeleted = false;
}
