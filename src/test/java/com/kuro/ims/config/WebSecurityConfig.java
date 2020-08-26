package com.kuro.ims.config;

import com.kuro.ims.config.security.JwtTokenUtil;
import com.kuro.ims.service.JwtUserDetailsService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public
class WebSecurityConfig
{

    @Bean
    public JwtUserDetailsService jwtUserDetailsService()
    {
        return Mockito.mock(JwtUserDetailsService.class);
    }


    @Bean
    public JwtTokenUtil jwtTokenUtil()
    {
        return Mockito.mock(JwtTokenUtil.class);
    }


    @Bean
    public AuthenticationManager authenticationManager()
    {
        return Mockito.mock(AuthenticationManager.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return Mockito.mock(PasswordEncoder.class);
    }

}
