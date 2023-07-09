package com.example.test.controller;

import com.example.test.model.dto.NewPost;
import com.example.test.model.dto.NewUser;
import com.example.test.model.entity.Posts;
import com.example.test.model.entity.Users;
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

    @MessageMapping("/registration")
    Mono<Users> registrationUser(NewUser user) {
        return userService.saveUser(user);
    }

    @MessageMapping("/login")
    Mono<Users> loginUser(NewUser user) {
        return userService.loginUser(user);
    }

    @MessageMapping("/users/{user-id}/posts")
    Mono<Posts> creatPost(@DestinationVariable("user-id") @NonNull String userId, NewPost post) {
        return userService.creatPost(userId, post);
    }

    @MessageMapping("/users/{user-id}/posts/{posts-id}")
    Mono<Posts> changeUserPostById(@DestinationVariable("user-id") @NonNull String userId,
                                   @DestinationVariable("posts-id") @NonNull String postId,
                                   NewPost post) {
        return userService.changeUserPostById(userId, postId, post);
    }

    @MessageMapping("/posts")
    Flux<Posts> getAllPosts() {
        return userService.getPosts();
    }
}
