package com.example.test.user.service;

import com.example.test.user.model.entity.Posts;
import com.example.test.user.model.entity.Users;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserServiceInterface {
    Mono<Users> saveUser(Mono<Users> user);

    Mono<Users> loginUser(Mono<Users> user);

    Flux<Posts> getPosts();

    Mono<Posts> changeUserPostById(String userId, String postId, Posts post);

    Mono<Posts> creatPost(String userId, Posts post);
}
