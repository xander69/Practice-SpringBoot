package org.xander.practice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xander.practice.webapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String activationCode);

    boolean existsByUsername(String username);
}
