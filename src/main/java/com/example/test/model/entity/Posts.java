package com.example.test.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Posts {
    @Id
    private String postId;
    private String title;
    private String body;
    @Field("dateCreated")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Instant dateCreated;
    private String userId;
}
