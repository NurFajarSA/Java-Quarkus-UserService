package me.nurfajar.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
@SQLDelete(sql = "UPDATE user_table SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class UserModel extends PanacheEntityBase {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(unique = true)
    private String email;

    @Username
    private String username;

    @Password
    private String password;

    @Enumerated(EnumType.STRING)
    @Roles
    private Role role;

    @Column(name = "date_create")
    private LocalDateTime dateCreate = LocalDateTime.now();

    @Column(name = "date_update")
    private LocalDateTime dateUpdate = LocalDateTime.now();

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "login_attempt")
    private int loginAttempt = 0;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public static UserModel findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public static UserModel findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public static long totalLoginAttempt() {
        Long totalLoginAttempts = find("select sum(loginAttempt) from UserModel").project(Long.class).firstResult();
        return totalLoginAttempts != null ? totalLoginAttempts : 0;
    }

}
