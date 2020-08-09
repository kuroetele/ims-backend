package com.kuro.ims.controller;

import com.kuro.ims.config.security.CustomUserDetails;
import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.User;
import com.kuro.ims.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController
{
    private final UserService userService;


    @GetMapping
    public Response<List<User>> getUsers()
    {
        return Response.<List<User>>builder()
            .data(userService.getUsers())
            .build();
    }


    @GetMapping("/me")
    public Response<User> getCurrentUser(Authentication authentication)
    {
        return Response.<User>builder()
            .data(userService.getUser(((CustomUserDetails) authentication.getPrincipal()).getId()))
            .build();
    }


    @PostMapping
    public void createUser(@RequestBody User user)
    {
        userService.createUser(user);
    }
}
