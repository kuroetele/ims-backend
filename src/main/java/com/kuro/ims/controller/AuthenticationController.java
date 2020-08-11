package com.kuro.ims.controller;

import com.kuro.ims.config.security.CustomUserDetails;
import com.kuro.ims.config.security.JwtRequest;
import com.kuro.ims.config.security.JwtResponse;
import com.kuro.ims.config.security.JwtTokenUtil;
import com.kuro.ims.dto.UpdatePasswordDto;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.service.JwtUserDetailsService;
import com.kuro.ims.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController
{

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private JwtUserDetailsService userDetailsService;

    private UserService userService;


    @PostMapping(value = {"/api/authenticate","/api/user-login"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception
    {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/api/update-password")
    public void updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto, Authentication authentication){
        userService.updatePassword(((CustomUserDetails) authentication.getPrincipal()).getId(), updatePasswordDto);
    }


    private void authenticate(String username, String password) throws Exception
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e)
        {
            throw new ImsClientException("User's account has been disabled", e);
        }
        catch (BadCredentialsException e)
        {
            throw new ImsClientException("Invalid username or password", e);
        }
    }
}
