package com.michael.thedoer.userservice.service;

import com.michael.thedoer.userservice.dto.UserDto;
import com.michael.thedoer.userservice.model.User;
import com.michael.thedoer.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public UserDto addUser(User user)
    {
        var userCreated = userRepository.save(user);
        return UserDto.builder()
                .email(user.getEmail())
                .id(userCreated.getId())
                .name(userCreated.getName())
                .build();
    }

    public boolean userExits(Long userId)
    {
        return userRepository.existsById(userId);
    }

    public UserDto getUserById(Long userId)
    {
        User userFound = userRepository.findById(userId).orElse(null);
        if(userFound==null) return null;
        return UserDto.builder()
                .email(userFound.getEmail())
                .name(userFound.getName())
                .id(userFound.getId())
                .build();


    }
}
