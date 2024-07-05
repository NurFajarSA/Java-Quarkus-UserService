package me.nurfajar.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
    @Pattern(regexp = "^(.+)@(.+)$", message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role must be ADMIN or USER")
    private String role;
}
