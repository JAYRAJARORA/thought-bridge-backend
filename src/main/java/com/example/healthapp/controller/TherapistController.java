package com.example.healthapp.controller;

import com.example.healthapp.model.User;
import com.example.healthapp.service.TherapistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Tag(name="Therapist details", description = "APIs for fetching therapist overview, " +
        "details and give ratings and reviews")
@RequestMapping("/therapists")
public class TherapistController {

    @Autowired
    private TherapistService therapistService;
    @Operation(
            summary = "Filter therapists",
            description = "Fetch all therapists. TODO: pagination part. Harcoded right now")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all therapists"),
            @ApiResponse(responseCode = "204", description = "No therapists present. No content"),
    })
    @GetMapping("")
    public ResponseEntity<List<User>> getTherapists() {
        Page<User> therapistPage = therapistService.getTherapists();
        List<User> therapists = therapistPage.getContent();

        if (therapists.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(therapists);
        }
    }
}
