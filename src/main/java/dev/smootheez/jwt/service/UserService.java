package dev.smootheez.jwt.service;

import dev.smootheez.jwt.dto.UpdateUserRequest;
import dev.smootheez.jwt.entity.User;
import dev.smootheez.jwt.dto.DeleteUserRequest;
import dev.smootheez.jwt.dto.AuthResponse;
import dev.smootheez.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse updateUser(String username, UpdateUserRequest request) {
        // Find the user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid current password");
        }

        // Update email if provided
        if (request.getNewEmail() != null && !request.getNewEmail().isEmpty()) {
            // Check if new email is already in use
            if (userRepository.existsByEmail(request.getNewEmail())) {
                throw new RuntimeException("Email is already in use");
            }
            user.setEmail(request.getNewEmail());
        }

        // Update password if provided
        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        // Save updated user
        userRepository.save(user);

        // Generate new JWT token
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .username(user.getUsername())
                .token(token)
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse deleteUser(DeleteUserRequest request) {
        // Find the user
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Delete the user
        userRepository.delete(user);

        return AuthResponse.builder().build();
    }
}