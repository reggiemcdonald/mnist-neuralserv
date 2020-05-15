package org.reggiemcdonald.persistence.repo;

import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Made with help from the easy to follow tutorial at
 * https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
 */

public interface AppUserRepository extends CrudRepository<AppUserEntity, Long> {
    AppUserEntity findByUsername(String username);
}
