package com.example.test.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class NewUser {
    private String userName;
    private String email;
    private String password;
}