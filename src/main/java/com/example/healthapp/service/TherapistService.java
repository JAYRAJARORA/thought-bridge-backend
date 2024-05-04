package com.example.healthapp.service;

import com.example.healthapp.dto.ReviewDTO;
import com.example.healthapp.exception.NotFoundException;
import com.example.healthapp.model.Review;
import com.example.healthapp.model.Therapist;
import com.example.healthapp.model.User;
import com.example.healthapp.repository.CategoryRepository;
import com.example.healthapp.repository.ReviewRepository;
import com.example.healthapp.repository.TherapistRepository;
import com.example.healthapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TherapistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TherapistRepository therapistRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public Page<User> getTherapists() {
        int page = 0;
        int size = 10;
        Sort.Direction direction = Sort.Direction.DESC;
        String[] properties = {"id"};
        Pageable pageable = PageRequest.of(page, size, direction, properties);
        return userRepository.findAllByRole("therapist", pageable);
    }

    public Therapist getTherapistById(String id) {
        return therapistRepository.findById(id).orElseThrow(() -> new NotFoundException("Therapist not found"));
    }

    public List<Therapist> filterByLocation(double longitude, double latitude, double radiusInMiles) {
        double radiusInMeters = radiusInMiles * 1609.34; // Convert miles to meters
        return therapistRepository.findByLocationNear(longitude, latitude, radiusInMeters);
    }

    @Transactional
    public ReviewDTO addOrUpdateReview(Review newReview, String therapistId) {
        // Fetch all reviews for the therapist
        List<Review> reviews = reviewRepository.findByTherapistId(therapistId);
        String userId = newReview.getUserId();
        Integer rating = newReview.getRating();

        // Calculate the new average rating based on existing reviews and the new/updated rating
        double newAverage = calculateNewAverage(reviews, rating, userId);

        // Update the therapist's average rating
        Therapist therapist = therapistRepository.findById(therapistId).orElseThrow(() -> new RuntimeException("Therapist not found"));
        therapist.setAvgRating(newAverage);
        therapistRepository.save(therapist);

        // Add or update the review
        Review review = reviews.stream()
                .filter(r -> r.getUserId().equals(userId))
                .findFirst()
                .orElse(new Review());

        review.setUserId(userId);
        review.setTherapistId(therapistId);
        review.setRating(rating);
        review.setContent(newReview.getContent());
        review.setDate(new Date());
        Review savedReview = reviewRepository.save(review);

        return convertToDto(savedReview);
    }

    private double calculateNewAverage(List<Review> reviews, Integer newRating, String userId) {
        int totalRating = reviews.stream()
                .filter(r -> !r.getUserId().equals(userId))
                .mapToInt(Review::getRating)
                .sum() + newRating;
        int count = (int) reviews.stream().filter(r -> !r.getUserId().equals(userId)).count() + 1;

        BigDecimal average = new BigDecimal(totalRating).divide(BigDecimal.valueOf(count), 1, RoundingMode.HALF_UP);
        return average.doubleValue();
    }

    public List<ReviewDTO> getReviewsForTherapist(String therapistId) {
        List<Review> reviews = reviewRepository.findByTherapistId(therapistId);
        return reviews.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ReviewDTO convertToDto(Review review) {
        User user = userRepository.findById(review.getUserId()).orElse(null);
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setTherapistId(review.getTherapistId());
        dto.setUserId(review.getUserId());
        dto.setUsername(user != null ? user.getUsername() : "Unknown"); // Handle null case
        dto.setRating(review.getRating());
        dto.setContent(review.getContent());
        dto.setDate(review.getDate());
        return dto;
    }
}
