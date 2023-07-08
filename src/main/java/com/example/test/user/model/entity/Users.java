package com.example.test.user.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "users")
public class Users {
    @Id
    private String userId;
    private String userName;
    private String password;
    private String email;
}
