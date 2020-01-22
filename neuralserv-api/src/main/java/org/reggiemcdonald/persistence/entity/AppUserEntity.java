package org.reggiemcdonald.persistence.entity;

import javax.persistence.*;

/**
 * Made with help from the easy to follow tutorial at
 * https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
 */

@Entity
@Table(name = "app_user")
public class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    public AppUserEntity() {}

    public AppUserEntity(String _username, String _password) {
        username = _username;
        password = _password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
