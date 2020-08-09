package com.kuro.ims.controller;

import com.kuro.ims.config.security.JwtRequest;
import com.kuro.ims.config.security.JwtResponse;
import com.kuro.ims.config.security.JwtTokenUtil;
import com.kuro.ims.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class JwtAuthenticationController
{

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private JwtUserDetailsService userDetailsService;


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception
    {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    private void authenticate(String username, String password) throws Exception
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e)
        {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
