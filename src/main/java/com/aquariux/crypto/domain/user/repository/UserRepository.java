package com.aquariux.crypto.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aquariux.crypto.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    User findByUserId(String userId);
}
