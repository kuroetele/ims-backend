package com.kuro.ims.config;

import com.kuro.ims.type.Role;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser
{

    String username() default "rob";

    String name() default "Rob Winch";

    Role role();

    long id() default 1L;
}
