package org.xander.practice.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xander.practice.webapp.entity.Role;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.security.AuthenticationFacade;
import org.xander.practice.webapp.service.UserService;

import java.util.Map;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @Autowired
    public UserController(AuthenticationFacade authenticationFacade,
                          UserService userService) {
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("username", authenticationFacade.getAuthentication().getName());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("username", authenticationFacade.getAuthentication().getName());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        userService.updateUser(user, username, form.get("password"), form.keySet());
        return "redirect:/user";
    }
}
