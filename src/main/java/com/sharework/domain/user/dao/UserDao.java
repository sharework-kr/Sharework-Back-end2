package com.sharework.domain.user.dao;

import com.sharework.domain.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {
    Optional<User> findById(String id);
}
