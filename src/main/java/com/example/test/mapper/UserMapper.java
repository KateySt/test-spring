package com.example.test.mapper;

import com.example.test.model.dto.NewUser;
import com.example.test.model.dto.User;
import com.example.test.model.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(Users users) {
        return User.builder()
                .userId(users.getUserId())
                .userName(users.getUserName())
                .password(users.getPassword())
                .email(users.getEmail())
                .build();
    }

    public Users toUsers(NewUser user) {
        return Users.builder()
                .userName(user.getUserName())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
