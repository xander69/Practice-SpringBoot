package org.xander.practice.webapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.exception.InvalidCaptchaException;
import org.xander.practice.webapp.exception.UserAlreadyExistsException;
import org.xander.practice.webapp.security.AuthFailureHandler;
import org.xander.practice.webapp.service.CaptchaService;
import org.xander.practice.webapp.service.UserService;
import org.xander.practice.webapp.util.ValidationHelper;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class LoginController {

    private final UserService userService;
    private final AuthFailureHandler authFailureHandler;
    private final CaptchaService captchaService;
    private final boolean recaptchaUse;

    @Autowired
    public LoginController(UserService userService,
                           AuthFailureHandler authFailureHandler,
                           CaptchaService captchaService,
                           @Value("${recaptcha.use:false}") boolean recaptchaUse) {
        this.userService = userService;
        this.authFailureHandler = authFailureHandler;
        this.captchaService = captchaService;
        this.recaptchaUse = recaptchaUse;
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
    public String register(Model model) {
        model.addAttribute("recaptchaUse", recaptchaUse);
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@Valid User user,
                          BindingResult bindingResult,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          Model model) {
        Map<String, String> errorsMap = new HashMap<>();
        if (!Objects.equals(user.getPassword(), user.getPassword2())) {
            errorsMap.put("password2Error", "Passwords are different!");
        }
        try {
            captchaService.checkCaptcha(captchaResponse);
        } catch (InvalidCaptchaException e) {
            errorsMap.put("captchaError", e.getMessage());
        }
        if (bindingResult.hasErrors()) {
            errorsMap.putAll(ValidationHelper.getErrorsMap(bindingResult));
        }
        if (!errorsMap.isEmpty()) {
            model.addAttribute("recaptchaUse", recaptchaUse);
            model.mergeAttributes(errorsMap);
            return "register";
        }
        try {
            User newUser = userService.createUser(user);
            log.info("Created user with id=[{}]", newUser.getId());
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("recaptchaUse", recaptchaUse);
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
