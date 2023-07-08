package com.example.test.user.controller;

import com.example.test.user.model.entity.Posts;
import com.example.test.user.model.entity.Users;
import com.example.test.user.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
@AllArgsConstructor
public class RsocketController {
    private UserServiceImpl userService;

    @MessageMapping("/registration")
    Mono<Users> registrationUser(Users user) {
        log.info("registrationUser {}", user.toString());
        return userService.saveUser(Mono.just(user));
    }

    @MessageMapping("/login")
    Mono<Users> loginUser(Users user) {
        log.info("login {}", user.toString());
        return userService.loginUser(Mono.just(user));
    }

    @MessageMapping("/users/{user-id}/posts")
    Mono<Posts> creatPost(@DestinationVariable("user-id") String userId, Posts post) {
        return userService.creatPost(userId, post);
    }

    @MessageMapping("/users/{user-id}/posts/{posts-id}")
    Mono<Posts> changeUserPostById(@DestinationVariable("user-id") String userId,
                                   @DestinationVariable("posts-id") String postId,
                                   Posts post) {
        return userService.changeUserPostById(userId, postId, post);
    }

    @MessageMapping("/posts")
    Flux<Posts> getPosts() {
        return userService.getPosts();
    }

}
