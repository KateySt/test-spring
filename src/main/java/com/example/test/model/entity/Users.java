package com.example.test.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Users {
    @Id
    private String userId;
    private String userName;
    private String password;
    private String email;
}
