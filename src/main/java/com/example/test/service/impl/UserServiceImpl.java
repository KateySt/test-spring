package com.example.test.service.impl;

import com.example.test.model.dto.NewPost;
import com.example.test.model.dto.NewUser;
import com.example.test.model.entity.Posts;
import com.example.test.model.entity.Users;
import com.example.test.repository.PostRepository;
import com.example.test.repository.UserRepository;
import com.example.test.service.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserServiceInterface {
    private UserRepository userRepository;

    private PostRepository postRepository;

    @Override
    public Mono<Users> saveUser(NewUser user) {
        return userRepository.existsByEmail(user.email())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new NoSuchElementException());
                    }
                    return userRepository.insert(Users.builder()
                            .userName(user.fullName())
                            .password(user.password())
                            .email(user.email())
                            .build());
                });
    }

    @Override
    public Mono<Users> loginUser(NewUser user) {
        return userRepository.existsByEmail(user.email())
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new NoSuchElementException());
                    }
                    return userRepository.findByEmail(user.email());
                });
    }

    @Override
    public Flux<Posts> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Mono<Posts> changeUserPostById(String userId, String postId, NewPost post) {
        return postRepository.existsByUserIdAndPostId(userId, postId).flatMap(exists -> {
            if (!exists) {
                return Mono.error(new NoSuchElementException());
            }
            return postRepository.findByPostId(postId)
                    .map(oldPost -> {
                        if (!userId.equals(oldPost.getUserId())) {
                            throw new NoSuchElementException();
                        }
                        if (post.getTitle() != null) {
                            oldPost.setTitle(post.getTitle());
                        }
                        if (post.getBody() != null) {
                            oldPost.setBody(post.getBody());
                        }
                        return oldPost;
                    })
                    .flatMap(postRepository::save);
        });
    }

    @Override
    public Mono<Posts> creatPost(String userId, NewPost newPost) {
        Posts post = Posts.builder()
                .userId(userId)
                .dateCreated(Instant.now())
                .title(newPost.getTitle())
                .body(newPost.getBody())
                .build();
        return postRepository.save(post);
    }

}
