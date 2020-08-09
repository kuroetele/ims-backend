package com.kuro.ims.service;

import com.kuro.ims.entity.User;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService
{
    private final UserRepository userRepository;


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

        userRepository.save(user);
    }


    public Long getUserCount()
    {
        return this.userRepository.count();
    }
}
