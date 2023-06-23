package com.sharework.repository;

import com.sharework.models.ERole;
import com.sharework.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
