package com.example.healthapp.repository;

import com.example.healthapp.model.Therapist;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TherapistRepository extends MongoRepository<Therapist, String> {
    @Query("{'address.location': {'$near': {'$geometry': {'type': 'Point', 'coordinates': [?0, ?1]}, '$maxDistance': ?2}}}")
    List<Therapist> findByLocationNear(double longitude, double latitude, double distance);
}
