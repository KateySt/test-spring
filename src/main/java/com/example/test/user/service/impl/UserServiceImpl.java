package com.example.test.user.service.impl;

import com.example.test.user.model.entity.Posts;
import com.example.test.user.model.entity.Users;
import com.example.test.user.repository.PostRepository;
import com.example.test.user.repository.UserRepository;
import com.example.test.user.service.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserServiceInterface {
    private UserRepository userRepository;

    private PostRepository postRepository;

    @Override
    public Mono<Users> saveUser(Mono<Users> user) {
        return user.flatMap(userRepository::insert);
    }

    @Override
    public Mono<Users> loginUser(Mono<Users> user) {
        return user.flatMap(u -> {
            String email = u.getEmail();
            String password = u.getPassword();
            Optional<Users> foundUser = userRepository.findByEmailAndPassword(email, password);
            if (foundUser.isPresent()) {
                return Mono.just(foundUser.get());
            } else {
                return Mono.error(new RuntimeException("Invalid email or password"));
            }
        });
    }

    @Override
    public Flux<Posts> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Mono<Posts> changeUserPostById(String userId, String postId, Posts post) {
        if (!postRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new NoSuchElementException();
        }
        post.setDateCreated(Instant.now());
        return postRepository.findByPostId(postId)
                .flatMap(oldPost -> {
                    if (post.getTitle() != null) {
                        oldPost.setTitle(post.getTitle());
                    }
                    if (post.getText() != null) {
                        oldPost.setText(post.getText());
                    }
                    oldPost.setDateCreated(Instant.now());
                    return postRepository.save(oldPost);
                });
    }

    @Override
    public Mono<Posts> creatPost(String userId, Posts post) {
        post.setUserId(userId);
        post.setDateCreated(Instant.now());
        return postRepository.save(post);
    }
}
