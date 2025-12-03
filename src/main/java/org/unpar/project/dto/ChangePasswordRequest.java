package org.unpar.project.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank
    @Size(min = 8, max = 20, message = "Password harus berada di antara 8 dan 20 karakter")
    private String password;

    @NotBlank
    @Size(min = 8, max = 20, message = "Password harus berada di antara 8 dan 20 karakter")
    private String confirmPassword;
}
