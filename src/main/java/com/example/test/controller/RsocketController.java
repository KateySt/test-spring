package com.example.test.controller;

import com.example.test.model.dto.*;
import com.example.test.service.PostServiceInterface;
import com.example.test.service.UserServiceInterface;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class RsocketController {
    private UserServiceInterface userService;
    private PostServiceInterface postService;

    @MessageMapping("registration")
    Mono<User> registrationUser(Mono<NewUser> user) {
        return userService.saveUser(user);
    }

    @MessageMapping("login")
    Mono<User> loginUser(Mono<LogUser> user) {
        return userService.loginUser(user);
    }

    @MessageMapping("createPost")
    Mono<Post> creatPost(Mono<NewPost> post) {
        return postService.createPost(post);
    }

    @MessageMapping("changePost")
    Mono<Post> changeUserPostById(Mono<ChangedPost> post) {
        return postService.changeUserPostById(post);
    }

    @MessageMapping("posts")
    Flux<Post> getAllPosts() {
        return postService.getPosts();
    }
}
