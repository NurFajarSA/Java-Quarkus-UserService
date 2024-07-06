package me.nurfajar.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user_table SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class UserModel extends PanacheEntityBase {
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

}
