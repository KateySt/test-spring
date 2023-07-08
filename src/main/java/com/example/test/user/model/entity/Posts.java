package com.example.test.user.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "posts")
public class Posts {
    @Id
    private String postId;
    private String title;
    private String text;
    private Instant dateCreated;
    private String userId;
}
