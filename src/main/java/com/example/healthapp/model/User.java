package com.example.healthapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
//    @Indexed(unique = true)
    private String email;
    private String password;

    private String role; // therapist or user
}