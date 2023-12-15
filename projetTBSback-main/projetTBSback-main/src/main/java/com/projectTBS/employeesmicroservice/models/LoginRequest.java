package com.projectTBS.employeesmicroservice.models;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;

    // Getters et setters
}
