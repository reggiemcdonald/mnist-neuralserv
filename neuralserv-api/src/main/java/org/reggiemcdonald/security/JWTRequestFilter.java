package org.reggiemcdonald.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.jboss.logging.Logger;
import org.reggiemcdonald.config.token.Token;
import org.reggiemcdonald.service.JWTUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
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
public class JWTRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final Logger logger = Logger.getLogger(JWTRequestFilter.class);

    private Token jwtToken;
    private JWTUserDetailsService service;

    @Autowired
    public JWTRequestFilter(Token _token, JWTUserDetailsService _service) {
        jwtToken = _token;
        service = _service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String tokenHeader = httpServletRequest.getHeader(AUTHORIZATION);
        String requestToken = null;
        String username = null;
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            requestToken = tokenHeader.substring(7);
            try {
                username = jwtToken.username(requestToken);
            } catch (IllegalArgumentException iae) {
                // TODO: Improve error handling
                System.out.println(iae.getMessage());
            } catch (ExpiredJwtException ee) {
                // Improve this
                System.out.println(ee.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails details = service.loadUserByUsername(username);
            if (jwtToken.validate(requestToken, details)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                details,
                                null,
                                details.getAuthorities()
                        );
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
