package org.xander.practice.webapp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.xander.practice.webapp.entity.Role;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.exception.UserAlreadyExistsException;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       MailSender mailSender,
                       @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
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

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
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
        user.setPassword(passwordEncoder.encode(inputData.get("password")));
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
        boolean isEmailChanged = !Objects.equals(email, user.getUsername());
        if (isEmailChanged) {
            user.setEmail(email);
            if (StringUtils.isNotBlank(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (StringUtils.isNotBlank(password) && !Objects.equals(password, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password));
        }
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

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepository.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}
