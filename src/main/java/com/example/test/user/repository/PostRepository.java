package com.example.test.user.repository;

import com.example.test.user.model.entity.Posts;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Posts, String> {
    Mono<Posts> findByPostId(String postId);

    boolean existsByUserIdAndPostId(String userId, String postId);
}

