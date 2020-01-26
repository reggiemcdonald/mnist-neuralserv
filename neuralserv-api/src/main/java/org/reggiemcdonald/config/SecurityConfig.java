package org.reggiemcdonald.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.reggiemcdonald.config.token.JWTToken;
import org.reggiemcdonald.config.token.Token;
import org.reggiemcdonald.security.JWTAuthenticationEntry;
import org.reggiemcdonald.security.JWTRequestFilter;
import org.reggiemcdonald.service.JWTUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Adapted from:
 * https://dev.to/cuongld2/create-apis-with-jwt-authorization-using-spring-boot-24f9
 * This is my first time working with JWT. I consulted this resource to learn more about
 * how they work in Java. In no way do I declare this class to be a work of my own
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    JWTAuthenticationEntry jwtAuthenticationEntry;
    JWTRequestFilter filter;

    @Autowired
    JWTUserDetailsService service;

    @Autowired
    public SecurityConfig(JWTAuthenticationEntry _jwtAuthenicationEntry, @Lazy JWTRequestFilter _filter) {
        jwtAuthenticationEntry = _jwtAuthenicationEntry;
        filter = _filter;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public Token token() {
        return new JWTToken(50000, SignatureAlgorithm.HS256);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/sign-up", "/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntry)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        security
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }


}
