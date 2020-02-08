package org.reggiemcdonald.api.controller;

import org.jboss.logging.Logger;
import org.reggiemcdonald.api.model.api.AppUserApiModel;
import org.reggiemcdonald.config.token.Token;
import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.reggiemcdonald.persistence.entity.Role;
import org.reggiemcdonald.persistence.repo.AppUserRepository;
import org.reggiemcdonald.persistence.repo.RoleRepository;
import org.reggiemcdonald.service.JWTUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {

    private static final Logger logger = Logger.getLogger(AppUserController.class);

    private AuthenticationManager auth;
    private PasswordEncoder encoder;
    protected AppUserRepository repository;
    protected RoleRepository roleRepository;
    protected UserDetailsService service;
    private Token token;

    @Autowired
    public AppUserController(AuthenticationManager _auth, PasswordEncoder _encoder,
                             AppUserRepository _repository, RoleRepository _roleRepository,
                             JWTUserDetailsService _service, Token _token) {
        auth = _auth;
        encoder = _encoder;
        repository = _repository;
        roleRepository = _roleRepository;
        service = _service;
        token = _token;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<Boolean> addUser(@RequestBody AppUserApiModel model) {
        AppUserEntity entity = new AppUserEntity(
                model.getUsername(),
                encoder.encode(model.getPassword()),
                roleRepository.findByName("ROLE_USER")
        );
        AppUserEntity s = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody AppUserApiModel model) {
        try {
            auth.authenticate(
                    new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword())
            );
            UserDetails details = service.loadUserByUsername(model.getUsername());
            String s = token.create(details);
            return ResponseEntity
                    .status(200)
                    .body(s);
        } catch (BadCredentialsException be) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Failed to log you in");
        } catch (DisabledException exception) {
            logger.warn(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error has occurred");
        }
    }
}
