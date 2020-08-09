package com.kuro.ims.service;

import com.kuro.ims.entity.User;
import com.kuro.ims.repository.UserRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService
{
    private final UserRepository userRepository;


    public User getUser(Long id)
    {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
    }


    public List<User> getUsers()
    {
        return userRepository.findAll();
    }


    public void createUser(User user)
    {

    }
}
