package org.xander.practice.webapp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.xander.practice.webapp.entity.Role;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.exception.InvalidValueException;
import org.xander.practice.webapp.exception.RegistrationException;
import org.xander.practice.webapp.repository.UserRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository,
                       MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
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

    public User createUser(String username, String password, String email) {
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
        user.setEmail(email);
        user.setActive(Boolean.FALSE);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setCreateDateTime(new Date());
        user.setChangeDateTime(new Date());
        user.setRoles(Collections.singleton(Role.USER));
        sendActivationMessage(user);
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
        user.setEmail(inputData.get("email"));
        user.setChangeDateTime(new Date());
        return userRepository.save(user);
    }

    public boolean activateUser(String activationCode) {
        User user = userRepository.findByActivationCode(activationCode);
        if (Objects.isNull(user)) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        user.setChangeDateTime(new Date());
        userRepository.save(user);
        return true;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void updateProfile(User user, String password, String email) {
        if (StringUtils.isBlank(password)) {
            throw new InvalidValueException("Password must not be empty");
        }
        boolean isEmailChanged = !Objects.equals(email, user.getUsername());
        if (isEmailChanged) {
            user.setEmail(email);
            if (StringUtils.isNotBlank(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        user.setPassword(password);
        user.setChangeDateTime(new Date());
        userRepository.save(user);
        if (isEmailChanged) {
            sendActivationMessage(user);
        }
    }

    private void sendActivationMessage(User user) {
        if (StringUtils.isNotBlank(user.getEmail())) {
            String baseUrl =
                    ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            String message = String.format("" +
                            "Hello, %s!\n" +
                            "Welcome to Web Application.\n" +
                            "Please, visit next link: %s/activate/%s",
                    user.getUsername(), baseUrl, user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }
}
