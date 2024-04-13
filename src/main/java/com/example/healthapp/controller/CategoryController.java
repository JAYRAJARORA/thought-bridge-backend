package com.example.healthapp.controller;

import com.example.healthapp.model.Category;
import com.example.healthapp.model.Discussion;
import com.example.healthapp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Tag(name="Category related details", description = "APIs for fetching categories and respective discussions")
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Get categories",
            description = "Fetch all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all categories"),
    })
    @GetMapping("")
    public List<Category> getAllCategories() {
        return categoryService.fetchAllCategories();
    }

    @Operation(
            summary = "Get all articles for the categories",
            description = "Fetch all articles for the home page filtered by category IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all articles by categories"),
    })
    @GetMapping("/articles")
    public ResponseEntity<List<Discussion>> getArticlesByCategory(@RequestParam String categoryIds) {
        List<Discussion> articles = categoryService.getArticlesByCategory(categoryIds);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(articles);
        }
    }
}
