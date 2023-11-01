package com.ziorye.proofread.repository;

import com.ziorye.proofread.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByName(String name);
}
