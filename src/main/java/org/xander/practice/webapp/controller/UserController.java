package org.xander.practice.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xander.practice.webapp.entity.Role;
import org.xander.practice.webapp.entity.User;
import org.xander.practice.webapp.service.UserService;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "userList";
    }

    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        userService.updateUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("{user}/del")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userDelete(@PathVariable User user) {
        userService.deleteUser(user);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email) {
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }
}
