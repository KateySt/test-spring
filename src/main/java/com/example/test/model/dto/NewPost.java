package com.example.test.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class NewPost {
    private String authorId;
    private String title;
    private String body;
}
