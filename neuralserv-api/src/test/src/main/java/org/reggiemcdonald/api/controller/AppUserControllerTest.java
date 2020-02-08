package org.reggiemcdonald.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.reggiemcdonald.api.model.api.AppUserApiModel;
import org.reggiemcdonald.config.token.JWTToken;
import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.reggiemcdonald.persistence.entity.Role;
import org.reggiemcdonald.persistence.repo.AppUserRepository;
import org.reggiemcdonald.persistence.repo.MockAppUserRepository;
import org.reggiemcdonald.persistence.repo.MockRoleRepository;
import org.reggiemcdonald.persistence.repo.RoleRepository;
import org.reggiemcdonald.service.JWTUserDetailsService;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AppUserControllerTest {

    private AppUserRepository appUserRepository;
    private RoleRepository roleRepository;

    private AppUserController controller;

    private final String TOKEN_STRING = "TOKEN_STRING";

    @Before
    public void runBefore() {
        // Authentication Manager
        AuthenticationManager manager = mock(AuthenticationManager.class);
        // PasswordEncoder
        PasswordEncoder encoder = mockEncoder();
        // AppUserRepository and RoleRepository
        MockAppUserRepository mockAppUserRepository = new MockAppUserRepository();
        MockRoleRepository mockRoleRepository = new MockRoleRepository();
        appUserRepository = mockAppUserRepository.getRepository();
        roleRepository = mockRoleRepository.getRepository();
        // UserDetailsService
        JWTUserDetailsService userDetailsService = mockUserDetails();
        // Token
        JWTToken token = mockToken();

        controller = new AppUserController(
                manager,
                encoder,
                appUserRepository,
                roleRepository,
                userDetailsService,
                token
        );
        System.out.println(controller);
    }

    /*
     * Should create a user and return true
     */
    @Test
    public void testAppUserCreation() {
        AppUserApiModel model = model();
        controller.addUser(model);
        AppUserEntity entity = appUserRepository.findByUsername(model.getUsername());
        assertNotNull(entity);
        assertEquals(entity.getUsername(), model.getUsername());
        Collection<Role> roles = entity.getRoles();
        assertEquals(roles.size(), 1);
        assertEquals(roles.iterator().next(), roleRepository.findByName("ROLE_USER"));
    }

    @Test
    public void testAppUserLogin() {
        AppUserApiModel model = model();
        controller.addUser(model);
        ResponseEntity<String> res = controller.login(model);
        String token = res.getBody();
        assertEquals(token, TOKEN_STRING);
    }

    private AppUserApiModel model() {
        String testUser = "reggiemcdonald";
        String testPassword = "password";
        AppUserApiModel model = new AppUserApiModel();
        model.setUsername(testUser);
        model.setPassword(testPassword);
        return model;
    }

    private PasswordEncoder mockEncoder() {
        String encryptedPassword = "jfadvitnfjnjaf3fee";
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        when(encoder.encode(anyString()))
                .thenReturn(encryptedPassword);
        return encoder;
    }

    private JWTUserDetailsService mockUserDetails() {
        JWTUserDetailsService service = mock(JWTUserDetailsService.class);
        when(service.loadUserByUsername(anyString()))
                .thenAnswer(invocationOnMock -> {
                    String name = invocationOnMock.getArgument(0);
                    AppUserEntity entity = appUserRepository.findByUsername(name);
                    if (entity == null)
                        throw new UsernameNotFoundException("User does not exists");
                    String password = entity.getPassword();
                    Collection<GrantedAuthority> authorities = new LinkedList<>();
                    for (Role role: entity.getRoles())
                        authorities.addAll(role.getPrivileges());
                    return new User(
                            name,
                            password,
                            authorities
                    );
                });
        return service;
    }

    private JWTToken mockToken() {
        JWTToken token = mock(JWTToken.class);
        when(token.create(any(UserDetails.class)))
                .thenReturn(TOKEN_STRING);
        when(token.validate(eq(TOKEN_STRING), any()))
                .thenReturn(true);
        when(token.validate(not(eq(TOKEN_STRING)), any()))
                .thenReturn(false);
        return token;
    }
}
