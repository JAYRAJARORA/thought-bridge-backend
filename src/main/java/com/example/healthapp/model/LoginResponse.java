package com.example.healthapp.model;

import lombok.Data;

@Data
public class LoginResponse {
    public String username;
    public String role;
    public String userId;
}
