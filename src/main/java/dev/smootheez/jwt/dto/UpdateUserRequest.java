package dev.smootheez.jwt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Current password is required")
    private String currentPassword;
    @Email(message = "Invalid email format")
    private String newEmail;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String newPassword;
}
