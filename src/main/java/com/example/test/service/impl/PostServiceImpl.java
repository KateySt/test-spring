package com.example.test.service.impl;

import com.example.test.mapper.PostMapper;
import com.example.test.model.dto.NewPost;
import com.example.test.model.dto.Post;
import com.example.test.model.entity.Posts;
import com.example.test.repository.PostRepository;
import com.example.test.service.PostServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostServiceInterface {

    private PostRepository postRepository;
    private final String DATA_SORT = "dateCreated";
    private PostMapper postMapper;

    @Override
    public Flux<Post> getPosts() {//TODO sort by query parameter
        Sort sort = Sort.by(DATA_SORT).descending();
        return postRepository.findAll(sort)
                .map(postMapper::toPost);
    }

    @Override
    public Mono<Post> changeUserPostById(String userId, String postId, Mono<NewPost> post) {
        return postRepository.existsByUserIdAndPostId(userId, postId)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new NoSuchElementException());
                    }
                    return postRepository.findByPostId(postId)
                            .flatMap(oldPost -> {
                                if (!userId.equals(oldPost.getUserId())) {
                                    return Mono.error(new NoSuchElementException());
                                }
                                return post.map(newPost -> {
                                    if (newPost.getTitle() != null) {
                                        oldPost.setTitle(newPost.getTitle());
                                    }
                                    if (newPost.getBody() != null) {
                                        oldPost.setBody(newPost.getBody());
                                    }
                                    return oldPost;
                                });
                            })
                            .flatMap(postRepository::save)
                            .map(postMapper::toPost);
                });
    }

    @Override
    public Mono<Post> createPost(String userId, Mono<NewPost> newPost) {
        return newPost.flatMap(post -> {
            Posts postEntity = Posts.builder()
                    .userId(userId)
                    .dateCreated(Instant.now())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .build();
            return postRepository.save(postEntity)
                    .map(postMapper::toPost);
        });
    }
}
