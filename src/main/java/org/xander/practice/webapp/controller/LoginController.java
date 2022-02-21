package org.xander.practice.webapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.exception.UserAlreadyExistsException;
import org.xander.practice.webapp.security.AuthFailureHandler;
import org.xander.practice.webapp.service.UserService;
import org.xander.practice.webapp.util.ValidationHelper;

import javax.validation.Valid;
import java.util.Map;
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
            model.addAttribute("loginMessageType", "danger");
            model.addAttribute("loginMessage", authFailureHandler.getAuthException().getMessage());
            authFailureHandler.setAuthException(null);
        }
        if (StringUtils.isNotBlank(logout)) {
            model.addAttribute("loginMessageType", "success");
            model.addAttribute("loginMessage", "You have been successfully logged out!");
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        if (!Objects.equals(user.getPassword(), user.getPassword2())) {
            model.addAttribute("password2Error", "Passwords are different!");
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationHelper.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            return "register";
        }
        try {
            User newUser = userService.createUser(user);
            log.info("Created user with id=[{}]", newUser.getId());
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("usernameError", e.getMessage());
            return "/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("loginMessageType", "success");
            model.addAttribute("loginMessage", "User successfully activated! Try Sign In...");
        } else {
            model.addAttribute("loginMessageType", "danger");
            model.addAttribute("loginMessage", "Activation code is not found!");
        }
        return "login";
    }
}
