package com.example.test.model.dto;

import lombok.Builder;

@Builder
public record NewUser(
        String fullName,
        String email,
        String password
) {
}