package com.security.poc.repository;

import com.security.poc.entity.UserGroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGroupRoleRepository extends JpaRepository<UserGroupRole, Long> {
    Optional<UserGroupRole> findByGroupIdAndUserId(Long groupId, Long userId);
}