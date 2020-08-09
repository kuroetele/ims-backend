package com.kuro.ims.service;

import com.kuro.ims.config.security.CustomUserDetails;
import com.kuro.ims.entity.User;
import com.kuro.ims.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService
{

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username)
    {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(user);
    }
}
