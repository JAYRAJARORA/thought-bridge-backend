package com.example.healthapp.repository;

import com.example.healthapp.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<Review> findByUserIdAndTherapistId(String userId, String therapistId);

    List<Review> findByTherapistId(String therapistId);
}
