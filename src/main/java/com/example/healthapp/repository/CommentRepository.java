package com.example.healthapp.repository;

import com.example.healthapp.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    public List<Comment> findAllByDiscussionId(String discussionId);
}
