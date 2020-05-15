package org.reggiemcdonald.persistence.entity;

import org.hibernate.collection.internal.PersistentList;
import org.hibernate.collection.spi.PersistentCollection;
import sun.awt.image.ImageWatched;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "app_user_role",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Collection<Role> roles;

    public AppUserEntity() {}

    public AppUserEntity(String _username, String _password) {
        username = _username;
        password = _password;
        roles    = new LinkedList<>();
    }

    public AppUserEntity(String _username, String _password, Role... _roles) {
        username = _username;
        password = _password;
        roles    = new LinkedList<>();
        roles.addAll(Arrays.asList(_roles));
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

    public Collection<Role> getRoles() {
        return Collections.unmodifiableCollection(roles);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void setRoles(Collection<Role> _roles) {
        roles = _roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserEntity entity = (AppUserEntity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
