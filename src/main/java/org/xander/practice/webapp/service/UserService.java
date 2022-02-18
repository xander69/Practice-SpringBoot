package org.xander.practice.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.xander.practice.webapp.entity.Role;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.exception.RegistrationException;
import org.xander.practice.webapp.repository.UserRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }

    public User createUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RegistrationException(String.format("User with name %s already exists", username));
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(Boolean.TRUE);
        user.setCreateDateTime(new Date());
        user.setChangeDateTime(new Date());
        user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    public User updateUser(User user, String username, String password, Set<String> roleNames) {
        user.getRoles().clear();
        for (String roleName : roleNames) {
            if (Role.getNames().contains(roleName)) {
                user.getRoles().add(Role.valueOf(roleName));
            }
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setChangeDateTime(new Date());
        return userRepository.save(user);
    }
}
