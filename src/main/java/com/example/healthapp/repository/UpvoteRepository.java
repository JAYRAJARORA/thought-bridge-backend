package com.example.healthapp.repository;

import com.example.healthapp.model.Upvote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UpvoteRepository extends MongoRepository<Upvote, String> {
    @Query("{ 'userId' : ?0 , 'discussionId' : ?1 }")
    public Upvote findByUserIdAndDiscussionId(String userId, String discussionId);
}
