//package com.sharework.repository;
//
//import com.sharework.models.User;
//import com.sharework.models.Users;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface UsersDao extends JpaRepository<Users, Long> {
//  Optional<User> findByUsername(String username);
//
//  Boolean existsByUsername(String username);
//
//  Boolean existsByEmail(String email);
//  Boolean existsByPhoneNumber(String phoneNumber);
//}
