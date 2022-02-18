package org.xander.practice.webapp.controller;

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
import org.xander.practice.webapp.service.UserService;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Username or Password is incorrect!");
        }
        if (logout != null) {
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
