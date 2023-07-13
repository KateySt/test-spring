package com.example.test.service.impl;

import com.example.test.mapper.PostMapper;
import com.example.test.model.dto.ChangedPost;
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
import java.util.function.Predicate;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostServiceInterface {

    private PostRepository postRepository;
    private final String DATA_SORT = "dateCreated";
    private PostMapper postMapper;

    @Override
    public Flux<Post> getPosts() {
        return postRepository.findAll(Sort.by(DATA_SORT).descending())
                .map(postMapper::toPost);
    }

    @Override
    public Mono<Post> changeUserPostById(Mono<ChangedPost> post) {
        return post.flatMap(changedPost ->
                postRepository.existsByUserIdAndPostId(changedPost.getAuthorId(), changedPost.getPostId())
                        .filter(Boolean::booleanValue)
                        .switchIfEmpty(Mono.error(new NoSuchElementException()))
                        .then(postRepository.findByPostId(changedPost.getPostId())
                                .filter(oldPost -> changedPost.getAuthorId().equals(oldPost.getUserId()))
                                .flatMap(oldPost -> {
                                    if (changedPost.getTitle() != null) {
                                        oldPost.setTitle(changedPost.getTitle());
                                    }
                                    if (changedPost.getBody() != null) {
                                        oldPost.setBody(changedPost.getBody());
                                    }
                                    return postRepository.save(oldPost);
                                })
                                .map(postMapper::toPost))
        );
    }

    @Override
    public Mono<Post> createPost(Mono<NewPost> newPost) {
        return newPost.map(post -> Posts.builder()
                        .userId(post.getAuthorId())
                        .dateCreated(Instant.now())
                        .title(post.getTitle())
                        .body(post.getBody())
                        .build())
                .flatMap(postRepository::save)
                .map(postMapper::toPost);
    }
}
