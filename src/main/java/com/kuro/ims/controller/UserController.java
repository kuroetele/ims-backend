package com.kuro.ims.controller;

import com.kuro.ims.config.security.CustomUserDetails;
import com.kuro.ims.dto.Response;
import com.kuro.ims.dto.UpdateUserDto;
import com.kuro.ims.entity.User;
import com.kuro.ims.service.UserService;
import com.kuro.ims.type.Role;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Response<User> getUser(@PathVariable Long id)
    {
        return Response.<User>builder()
            .data(userService.getUser(id))
            .build();
    }


    @GetMapping("/me")
    public Response<User> getCurrentUser(Authentication authentication)
    {
        return Response.<User>builder()
            .data(userService.getUser(((CustomUserDetails) authentication.getPrincipal()).getId()))
            .build();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public void createUser(@RequestBody @Valid User user)
    {
        userService.createUser(user);
    }


    @GetMapping("/roles")
    public Response<?> getRoles()
    {
        return Response.<Role[]>builder()
            .data(Role.values())
            .build();
    }


    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me")
    public void updateCurrentUser(@RequestBody @Valid UpdateUserDto updateUserDto, Authentication authentication)
    {
        User user = new User();
        BeanUtils.copyProperties(updateUserDto, user);
        userService.updateCurrentUser(((CustomUserDetails) authentication.getPrincipal()).getId(), user);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody @Valid User user)
    {
        userService.updateUser(id, user);
    }


}
