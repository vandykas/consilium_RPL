package org.unpar.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Field harus diisi")
    @Size(min = 8, max = 20, message = "Password harus berada di antara 8 dan 20 karakter")
    private String password;

    @NotBlank(message = "Field harus diisi")
    @Size(min = 8, max = 20, message = "Password harus berada di antara 8 dan 20 karakter")
    private String confirmPassword;
}
