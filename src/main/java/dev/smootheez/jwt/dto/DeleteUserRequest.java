package dev.smootheez.jwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteUserRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
}
