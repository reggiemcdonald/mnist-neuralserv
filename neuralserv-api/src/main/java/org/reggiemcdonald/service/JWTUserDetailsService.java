package org.reggiemcdonald.service;

import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.reggiemcdonald.persistence.entity.Role;
import org.reggiemcdonald.persistence.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * This class was based off of the work published here:
 * https://dev.to/cuongld2/create-apis-with-jwt-authorization-using-spring-boot-24f9
 * This is my first attempt at programming JWT with Spring Security. The code shown below is not my own work
 * I make no statement that the below code is my own
 */

@Service
public class JWTUserDetailsService implements UserDetailsService {
    private AppUserRepository repository;

    @Autowired
    public JWTUserDetailsService(AppUserRepository _repository) {
        repository = _repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUserEntity entity = repository.findByUsername(s);
        if (entity != null) {
            return new User(
                    entity.getUsername(),
                    entity.getPassword(),
                    authorities(entity.getRoles())
            );
        } else {
            throw new UsernameNotFoundException(String.format("User %s does not exist", s));
        }
    }

    private Collection<GrantedAuthority> authorities(Collection<Role> roles) {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.addAll(role.getPrivileges());
        }
        return authorities;
    }
}
