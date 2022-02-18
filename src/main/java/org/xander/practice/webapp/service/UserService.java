package org.xander.practice.webapp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new InternalAuthenticationServiceException("Username or Password is incorrect!");
        }
        if (!user.getActive()) {
            throw new InternalAuthenticationServiceException("User is not activated!");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }

    public User createUser(String username, String password) {
        if (StringUtils.isBlank(username)) {
            throw new RegistrationException("Username must not be empty");
        }
        if (StringUtils.isBlank(password)) {
            throw new RegistrationException("Password must not be empty");
        }
        if (userRepository.existsByUsername(username)) {
            throw new RegistrationException(String.format("User with name '%s' already exists", username));
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(Boolean.FALSE);
        user.setCreateDateTime(new Date());
        user.setChangeDateTime(new Date());
        user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    public User updateUser(User user, String username, Map<String, String> inputData) {
        user.getRoles().clear();
        for (String roleName : inputData.keySet()) {
            if (Role.getNames().contains(roleName)) {
                user.getRoles().add(Role.valueOf(roleName));
            }
        }
        user.setUsername(username);
        user.setPassword(inputData.get("password"));
        user.setActive(inputData.containsKey("active"));
        user.setChangeDateTime(new Date());
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
