package org.reggiemcdonald.config.token;

import org.reggiemcdonald.api.model.api.AppUserApiModel;
import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface Token {
    /**
     * Validate a given token
     * @param token
     * @param entity
     * @return
     */
    Boolean validate(String token, UserDetails entity);

    /**
     * Create a token given the user
     * @param entity
     * @return
     */
    String create(UserDetails entity);


    /**
     * Parse the username from the token
     * @param token
     * @return
     */
    String username(String token);
}
