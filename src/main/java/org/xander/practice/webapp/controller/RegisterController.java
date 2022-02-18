package org.xander.practice.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.exception.RegistrationException;
import org.xander.practice.webapp.service.UserService;

@Controller
public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registration() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(User user, Model model) {
        try {
            User newUser = userService.createUser(user.getUsername(), user.getPassword());
            log.info("Created user with id=[{}]", newUser.getId());
        } catch (RegistrationException e) {
            model.addAttribute("message", e.getMessage());
            return "/register";
        }
        return "redirect:/login";
    }
}
