package dev.smootheez.jwt.controller;

import dev.smootheez.jwt.dto.UpdateUserRequest;
import dev.smootheez.jwt.dto.DeleteUserRequest;
import dev.smootheez.jwt.dto.AuthResponse;
import dev.smootheez.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<AuthResponse> updateUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateUserRequest request
    ) {
        AuthResponse response = userService.updateUser(userDetails.getUsername(), request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<AuthResponse> deleteUser(
            @RequestBody DeleteUserRequest request
    ) {
        AuthResponse response = userService.deleteUser(request);
        return ResponseEntity.ok(response);
    }
}