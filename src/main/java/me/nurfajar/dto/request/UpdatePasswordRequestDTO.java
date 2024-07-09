package me.nurfajar.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDTO {
    private String id;
    private String oldPassword;
    private String newPassword;
}
