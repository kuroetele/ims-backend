package com.kuro.ims.config;

import com.kuro.ims.config.security.JwtTokenUtil;
import com.kuro.ims.service.JwtUserDetailsService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

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

}
