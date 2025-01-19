package com.security.poc.service.impl;

import com.security.poc.entity.User;
import com.security.poc.repository.UserRepository;
import com.security.poc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found with ID: "+ userId));
    }
}