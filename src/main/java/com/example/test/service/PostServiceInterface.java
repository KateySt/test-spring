package com.example.test.service;

import com.example.test.model.dto.ChangedPost;
import com.example.test.model.dto.NewPost;
import com.example.test.model.dto.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostServiceInterface {
    Flux<Post> getPosts();

    Mono<Post> changeUserPostById(Mono<ChangedPost> post);

    Mono<Post> createPost(Mono<NewPost> newPost);
}
