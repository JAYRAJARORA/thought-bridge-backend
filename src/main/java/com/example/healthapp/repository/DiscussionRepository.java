package com.example.healthapp.repository;

import com.example.healthapp.model.Discussion;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DiscussionRepository extends MongoRepository<Discussion, String> {

    @Query("{ $and: [ { 'category.$id': { $in: ?0 } }, { 'type': 'articles' } ] }")
    List<Discussion> findByCategoriesAndType(List<ObjectId> categoryIds);
    Page<Discussion> findByType(String type, Pageable pageable);

    @Query("{ 'type' : ?0, 'author.$id' : ?1 }")
    Page<Discussion> findByTypeAndUserId(String type, ObjectId userId, Pageable pageable);
    List<Discussion> findByTypeOrderByUpvotesDesc(String type, Pageable pageable);

}
