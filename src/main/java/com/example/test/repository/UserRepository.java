package com.example.test.repository;

import com.example.test.model.entity.Users;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<Users, String> {
    Mono<Users> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);
}
