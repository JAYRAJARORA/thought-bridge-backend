package com.example.healthapp.controller;

import com.example.healthapp.model.Therapist;
import com.example.healthapp.model.User;
import com.example.healthapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Tag(name="User details", description = "APIs for viewing and updating user profile")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Update user profile",
            description = "Update user detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all therapists"),
            @ApiResponse(responseCode = "204", description = "No therapists present. No content"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable String id, @RequestParam String type, @RequestBody Therapist profile) {
        if(type.equals("user")) {
            User user = userService.updateUserProfile(id, (User) profile);
            return ResponseEntity.ok(user);
        } else {
            Therapist therapist = userService.updateTherapistProfile(id, profile);
            return ResponseEntity.ok(therapist);
        }
    }

    @Operation(
            summary = "View user profile",
            description = "Fetch user detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all therapists"),
            @ApiResponse(responseCode = "204", description = "No therapists present. No content"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> viewUserProfile(@PathVariable String id, @RequestParam String type) {
        if(type.equals("user")) {
            User user = userService.getUser(id);
            return ResponseEntity.ok(user);
        } else {
            Therapist therapist = userService.getTherapistProfile(id);
            return ResponseEntity.ok(therapist);
        }
    }

    @Operation(
            summary = "View user profile",
            description = "Fetch user detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all therapists"),
            @ApiResponse(responseCode = "204", description = "No therapists present. No content"),
    })
    @GetMapping("/languages")
    public ResponseEntity<?> getLanguages() {
        return ResponseEntity.ok(userService.getLanguages());
    }

}

