package com.kuro.ims.service;

import com.kuro.ims.dto.UpdatePasswordDto;
import com.kuro.ims.entity.User;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.repository.UserRepository;
import com.kuro.ims.util.CommonUtil;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;


    public User getUser(Long id)
    {
        return userRepository.findById(id).orElseThrow(() -> new ImsClientException("user not found"));
    }


    public List<User> getUsers()
    {
        return userRepository.findAll();
    }


    @Transactional
    public void createUser(User user)
    {
        String password = CommonUtil.generatePassword();
        log.debug("Password generated => {}", password);
        userRepository.findByEmail(user.getEmail())
            .ifPresent(u -> {
                throw new ImsClientException("A user with the specified email already exist");
            });
        user.setPlainPassword(password);
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordChangeRequired(true);
        applicationEventPublisher.publishEvent(userRepository.save(user));
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
        user.setPasswordChangeRequired(false);
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
    }


    public void updateCurrentUser(Long id, User user)
    {
        User userToUpdate = getUser(id);

        Optional.ofNullable(user.getName())
            .ifPresent(userToUpdate::setName);
        Optional.ofNullable(user.getPhone())
            .ifPresent(userToUpdate::setPhone);
        Optional.ofNullable(user.getAddress())
            .ifPresent(userToUpdate::setAddress);
        Optional.ofNullable(user.getImage())
            .ifPresent(userToUpdate::setImage);

        userRepository.save(userToUpdate);
    }


    public void updateUser(Long id, User user)
    {
        User userToUpdate = getUser(id);

        Optional.ofNullable(user.getName())
            .ifPresent(userToUpdate::setName);
        Optional.ofNullable(user.getPhone())
            .ifPresent(userToUpdate::setPhone);
        Optional.ofNullable(user.getAddress())
            .ifPresent(userToUpdate::setAddress);
        Optional.ofNullable(user.getImage())
            .ifPresent(userToUpdate::setImage);
        Optional.ofNullable(user.getRole())
            .ifPresent(userToUpdate::setRole);

        userRepository.save(userToUpdate);
    }

}
