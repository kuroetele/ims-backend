package com.kuro.ims.service;

import com.kuro.ims.dto.UpdatePasswordDto;
import com.kuro.ims.entity.User;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public User getUser(Long id)
    {
        return userRepository.findById(id).orElseThrow(() -> new ImsClientException("user not found"));
    }


    public List<User> getUsers()
    {
        return userRepository.findAll();
    }


    public void createUser(User user)
    {
        userRepository.findByEmail(user.getEmail())
            .ifPresent(u -> {
                throw new ImsClientException("A user with the specified email already exist");
            });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public Long getUserCount()
    {
        return this.userRepository.count();
    }


    public void updatePassword(Long id, UpdatePasswordDto updatePasswordDto)
    {
        User user = this.getUser(id);
        if (!passwordEncoder.matches(updatePasswordDto.getCurrentPassword(), user.getPassword()))
        {
            throw new ImsClientException("Invalid current password");
        }
        if (updatePasswordDto.getCurrentPassword().equals(updatePasswordDto.getNewPassword()))
        {
            throw new ImsClientException("New password cannot be the same as the current one");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
    }
}
