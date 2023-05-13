package com.aquariux.crypto.domain.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aquariux.crypto.domain.user.User;
import com.aquariux.crypto.domain.user.repository.UserRepository;
import com.aquariux.crypto.domain.user.service.UserService;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User registerUser(User newUser) {
        return userRepository.save(newUser);
    }
    
}
