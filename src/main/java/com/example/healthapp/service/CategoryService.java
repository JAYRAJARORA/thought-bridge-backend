package com.example.healthapp.service;

import com.example.healthapp.model.Category;
import com.example.healthapp.model.Discussion;
import com.example.healthapp.repository.CategoryRepository;
import com.example.healthapp.repository.DiscussionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired private DiscussionRepository discussionRepository;

    public List<Category> fetchAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Discussion> getArticlesByCategory(String ids) {
        List<String> categoryIds = Arrays.stream(ids.split(",")).toList();
        List<ObjectId> categoryIdObjects = categoryIds.stream()
                .map(ObjectId::new)
                .toList();
        return discussionRepository.findByCategoriesAndType(categoryIdObjects);
    }
}
