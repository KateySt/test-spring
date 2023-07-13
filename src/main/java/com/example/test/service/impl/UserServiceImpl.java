package com.example.test.service.impl;

import com.example.test.mapper.UserMapper;
import com.example.test.model.dto.LogUser;
import com.example.test.model.dto.NewUser;
import com.example.test.model.dto.User;
import com.example.test.repository.UserRepository;
import com.example.test.service.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserServiceInterface {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public Mono<User> saveUser(Mono<NewUser> user) {
        return user.flatMap(newUser -> userRepository.existsByEmail(newUser.getEmail())
                .filter(((Predicate<Boolean>) Boolean::booleanValue).negate())
                .switchIfEmpty(Mono.error(new NoSuchElementException()))
                .then(userRepository.save(userMapper.toUsers(newUser)))
                .map(userMapper::toUser)
        );
    }

    @Override
    public Mono<User> loginUser(Mono<LogUser> user) {
        return user.flatMap(logUser -> userRepository.existsByEmail(logUser.getEmail())
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new NoSuchElementException()))
                .then(userRepository.findByEmail(logUser.getEmail()))
                .map(userMapper::toUser)
        );
    }
}
