package com.example.test.user.repository;

import com.example.test.user.model.entity.Users;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface UserRepository extends ReactiveMongoRepository<Users, String> {
    Optional<Users> findByEmailAndPassword(String email, String password);

    Mono<Users> findByEmail(String email);
}
