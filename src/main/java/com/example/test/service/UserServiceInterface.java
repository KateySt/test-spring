package com.example.test.service;

import com.example.test.model.dto.LogUser;
import com.example.test.model.dto.NewUser;
import com.example.test.model.dto.User;
import reactor.core.publisher.Mono;

public interface UserServiceInterface {
    Mono<User> saveUser(Mono<NewUser> user);

    Mono<User> loginUser(Mono<LogUser> user);
}
