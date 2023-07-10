package com.example.test.service;

import com.example.test.model.dto.NewPost;
import com.example.test.model.dto.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostServiceInterface {
    Flux<Post> getPosts();

    Mono<Post> changeUserPostById(String userId, String postId, Mono<NewPost> post);

    Mono<Post> createPost(String userId, Mono<NewPost> newPost);
}
