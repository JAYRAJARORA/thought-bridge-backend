package com.example.healthapp.repository;

import com.example.healthapp.model.Language;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LanguageRepository extends MongoRepository<Language, String> {
}
