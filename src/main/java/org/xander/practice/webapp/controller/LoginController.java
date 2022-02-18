package org.xander.practice.webapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.exception.RegistrationException;
import org.xander.practice.webapp.security.AuthFailureHandler;
import org.xander.practice.webapp.service.UserService;

import java.util.Objects;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final AuthFailureHandler authFailureHandler;

    @Autowired
    public LoginController(UserService userService,
                           AuthFailureHandler authFailureHandler) {
        this.userService = userService;
        this.authFailureHandler = authFailureHandler;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (Objects.nonNull(authFailureHandler.getAuthException())) {
            model.addAttribute("errorMessage", authFailureHandler.getAuthException().getMessage());
            authFailureHandler.setAuthException(null);
        }
        if (StringUtils.isNotBlank(logout)) {
            model.addAttribute("logoutMessage", "You have been successfully logged out!");
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(User user, Model model) {
        try {
            User newUser = userService.createUser(user.getUsername(), user.getPassword());
            log.info("Created user with id=[{}]", newUser.getId());
        } catch (RegistrationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/register";
        }
        return "redirect:/login";
    }
}
