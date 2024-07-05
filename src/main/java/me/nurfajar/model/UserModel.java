package me.nurfajar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Column
    private LocalDate dateCreate = LocalDate.now();

    @Column
    private LocalDate dateUpdate = LocalDate.now();

    @Column
    private LocalDate lastLogin;

    @Column
    private int loginAttempt = 0;
}
