package com.example.healthapp.controller;

import com.example.healthapp.model.LoginResponse;
import com.example.healthapp.model.Therapist;
import com.example.healthapp.model.User;
import com.example.healthapp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@Tag(name="Registration and Login", description = "APIs for creating users and therapists and authenticating them")
@RequestMapping("/")
public class AuthController {
    @Autowired
    private AuthService authService;

    private final Logger logger = LoggerFactory.getLogger(ForumController.class);

    @Operation(
            summary = "Create a user",
            description = "Create a user by the register user page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Created"),
            @ApiResponse(responseCode = "409", description = "User Conflict"),
            @ApiResponse(responseCode = "500", description = "Error in creating user")
    })
    @PostMapping("/users")
    private ResponseEntity<?> saveUser(@RequestBody User user) {
        return this.authService.createUserOrTherapist("user", user);
    }

    @Operation(
            summary = "Create a therapist",
            description = "Create a therapist by the register therapist page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Therapist Created"),
            @ApiResponse(responseCode = "409", description = "Therapist Conflict"),
            @ApiResponse(responseCode = "500", description = "Error in creating therapist")
    })
    @PostMapping("/therapists")
    private ResponseEntity<?> saveTherapist(@RequestBody Therapist therapist) {
        return this.authService.createUserOrTherapist("therapist", therapist);
    }

    @Operation(
            summary = "Authenticating a user/therapist",
            description = "Login the user based on the role and username/password specified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Logged In"),
    })
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user, @RequestParam String role) {
        LoginResponse response = authService.findUser(user, role);
        if (response.getUsername() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
