package com.example.healthapp.controller;

import com.example.healthapp.dto.ReviewDTO;
import com.example.healthapp.model.Review;
import com.example.healthapp.model.Therapist;
import com.example.healthapp.model.User;
import com.example.healthapp.service.TherapistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = "Get therapist by ID",
            description = "Get therapist by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches the details of the therapist"),
            @ApiResponse(responseCode = "404", description = "Therapist not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Therapist> getTherapistById(@PathVariable String id) {
        Therapist therapist = therapistService.getTherapistById(id);

        return ResponseEntity.ok(therapist);
    }
    @Operation(
            summary = "Get nearby therapists",
            description = "Gives nearby therapist based on location entered as well as the radius in miles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches the list of therapists within the radius")
    })
    @GetMapping("/nearby")
    public List<Therapist> getTherapistsNearby(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double radius) {
        return therapistService.filterByLocation(longitude, latitude, radius);
    }

    @Operation(
            summary = "add a review and rating on a therapist",
            description = "Add a review and rating on the therapist. Review content is optional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the review added")
    })
    @PostMapping("/{therapistId}/reviews")
    public ResponseEntity<?> addOrUpdateReview(
            @PathVariable String therapistId,
            @RequestBody Review newReview) {
        ReviewDTO review = therapistService.addOrUpdateReview(newReview, therapistId);
        return ResponseEntity.ok(review);
    }

    @Operation(
            summary = "Get the list of reviews for a therapist",
            description = "Get all reviews given on a therapist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of reviews")
    })
    @GetMapping("/{therapistId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable String therapistId) {
        List<ReviewDTO> reviews = therapistService.getReviewsForTherapist(therapistId);
        return ResponseEntity.ok(reviews);
    }
}
