package org.reggiemcdonald.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Consulted the following resource to write this code:
 * https://dev.to/cuongld2/create-apis-with-jwt-authorization-using-spring-boot-24f9
 * This is my first time working with JWT. I consulted this resource to learn more about
 * how they work in Java. In no way do I declare this class to be a work of my own
 *
 */


@Component
public class JWTAuthenticationEntry implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
