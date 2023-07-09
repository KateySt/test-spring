package com.example.test.service;

import com.example.test.model.dto.NewPost;
import com.example.test.model.dto.NewUser;
import com.example.test.model.entity.Posts;
import com.example.test.model.entity.Users;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserServiceInterface {
    Mono<Users> saveUser(NewUser user);

    Mono<Users> loginUser(NewUser user);

    Flux<Posts> getPosts();

    Mono<Posts> changeUserPostById(String userId, String postId, NewPost post);

    Mono<Posts> creatPost(String userId, NewPost post);
}
