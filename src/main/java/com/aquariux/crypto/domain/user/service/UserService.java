package com.aquariux.crypto.domain.user.service;

import com.aquariux.crypto.domain.user.User;

public interface UserService {
    User findByUserId(String userId);
    User registerUser(User newUser);
    
}
