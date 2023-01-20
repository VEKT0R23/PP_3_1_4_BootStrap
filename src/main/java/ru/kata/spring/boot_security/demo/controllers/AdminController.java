package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public String getAllUsers(Principal principal, Model model, @ModelAttribute("user") User user1) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("currentUser", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getRolesList());
        return "admin";
    }

    @GetMapping(value = "/{id}")
    public String getByIdUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getRolesList());
        return "user_details";
    }

    @PostMapping(value = "/")
    public String createUser(@ModelAttribute("user") User user1) {

        userService.addUser(user1);
        return "redirect:";
    }

    @PatchMapping(value = "/{id}")
    public String updatedUserById(@ModelAttribute("user") User user, @PathVariable("id") Long id) {

        userService.updateUser(id, user);
        return "redirect:";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:";
    }
}
