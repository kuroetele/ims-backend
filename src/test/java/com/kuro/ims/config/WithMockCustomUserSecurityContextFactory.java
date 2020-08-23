package com.kuro.ims.config;

import com.kuro.ims.config.security.CustomUserDetails;
import com.kuro.ims.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockCustomUser>
{
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser)
    {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = new User();
        user.setEmail(customUser.username());
        user.setName(customUser.name());
        user.setRole(customUser.role());
        user.setId(customUser.id());

        CustomUserDetails principal =
            new CustomUserDetails(user);
        Authentication auth =
            new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
