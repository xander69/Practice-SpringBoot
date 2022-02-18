package org.xander.practice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xander.practice.webapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
}
