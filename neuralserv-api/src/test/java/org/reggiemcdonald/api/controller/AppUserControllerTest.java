package org.reggiemcdonald.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggiemcdonald.api.model.api.AppUserApiModel;
import org.reggiemcdonald.config.token.JWTToken;
import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.reggiemcdonald.persistence.entity.Role;
import org.reggiemcdonald.persistence.repo.AppUserRepository;
import org.reggiemcdonald.persistence.repo.RoleRepository;
import org.reggiemcdonald.service.JWTUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserControllerTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private AppUserRepository repository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JWTUserDetailsService userDetailsService;

    @Mock
    private JWTToken token;

    @InjectMocks
    private AppUserController controller;

    private String username = "username";
    private String password = "password";
    private String encryptedPassword = "encrypted-password";

    @Before
    public void runBefore() {
        MockitoAnnotations.initMocks(this);
    }

    private Role newRegularRole() {
        return mock(Role.class);
    }

    /**
     * Creates a test instance of AppUserEntity with
     * as set username, password, and a regular user role
     * @return an instance of AppUserEntity
     */
    private AppUserEntity newAppUserEntity() {
        return new AppUserEntity(username, password, newRegularRole());
    }

    /**
     * Creates a test instance of AppUserApiModel with
     * a set username and password
     * @return AppUserApiModel
     */
    private AppUserApiModel newApiModel() {
        return new AppUserApiModel(username, password);
    }

    private void mockController(AppUserController controller) {
        when(roleRepository.findByName(anyString()))
                .thenReturn(newRegularRole());
        when(encoder.encode(any(CharSequence.class)))
                .thenReturn(encryptedPassword);
        when(repository.save(any(AppUserEntity.class)))
                .thenReturn(null);
    }

    @Test
    public void testAddUser_Success() {
        AppUserEntity entity = newAppUserEntity();
        mockController(controller);
        controller.addUser(newApiModel());
        verify(repository).save(entity);
    }

    @Test
    public void testLogin_Success() {
        String jwtTokenString = "validation token";
        mockController(controller);
        when(authManager.authenticate(any()))
                .thenReturn(mock(Authentication.class));
        when(userDetailsService.loadUserByUsername(username))
                .thenReturn(mock(UserDetails.class));
        when(token.create(any(UserDetails.class)))
                .thenReturn(jwtTokenString);
        ResponseEntity<String> response = controller.login(newApiModel());
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isEqualTo(jwtTokenString);
    }

    @Test
    public void testLogin_EncounteredBadCreds() {
        final BadCredentialsException e =
                new BadCredentialsException("errMsg");
        when(authManager.authenticate(any(Authentication.class)))
                .thenThrow(e);
        ResponseEntity<String> response = controller.login(newApiModel());
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody())
                .isEqualTo("Failed to log you in");
    }

    @Test
    public void testLogin_EncounteredDisabledException() {
        final DisabledException e = new DisabledException("errMsg");
        when(authManager.authenticate(any(Authentication.class)))
                .thenThrow(e);
        ResponseEntity<String> response = controller.login(newApiModel());
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody())
                .isEqualTo("An error has occurred");
    }
}
