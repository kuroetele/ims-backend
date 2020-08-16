package com.kuro.ims.config.security;

import com.kuro.ims.entity.User;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails
{
    private User user;


    public CustomUserDetails(User user)
    {
        this.user = user;
    }


    public Long getId()
    {
        return this.user.getId();
    }

    public User getUser(){
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return AuthorityUtils.createAuthorityList("ROLE_" + this.user.getRole().name());
    }


    @Override
    public String getPassword()
    {
        return this.user.getPassword();
    }


    @Override
    public String getUsername()
    {
        return this.user.getEmail();
    }


    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }


    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }


    @Override
    public boolean isEnabled()
    {
        return !this.user.isDeleted();
    }
}
