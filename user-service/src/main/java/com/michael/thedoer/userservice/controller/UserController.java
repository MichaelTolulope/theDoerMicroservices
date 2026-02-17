package com.michael.thedoer.userservice.controller;

import com.michael.thedoer.userservice.dto.UserDto;
import com.michael.thedoer.userservice.model.User;
import com.michael.thedoer.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String hello() {
        return "Hello from User Service";
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getOneUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/user-exists/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean userExists(@PathVariable Long id) {
        return userService.userExits(id);
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(
                User.builder()
                        .email(userDto.getEmail())
                        .name(userDto.getName())
                        .build()
        );
    }
}
