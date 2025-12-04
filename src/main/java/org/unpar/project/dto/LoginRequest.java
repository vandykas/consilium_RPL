package org.unpar.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Field harus diisi")
    @Email(message = "Format email salah. Contoh: nama@example.com")
    private String email;

    @NotBlank(message = "Field harus diisi")
    @Size(min = 8, max = 20, message = "Password harus di antara 8 hingga 20 karakter")
    private String password;
}
