package me.nurfajar.dto.response;

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
