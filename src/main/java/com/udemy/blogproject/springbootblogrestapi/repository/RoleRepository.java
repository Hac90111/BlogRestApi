package com.udemy.blogproject.springbootblogrestapi.repository;

import com.udemy.blogproject.springbootblogrestapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
