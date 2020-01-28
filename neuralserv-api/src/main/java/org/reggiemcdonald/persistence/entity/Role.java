package org.reggiemcdonald.persistence.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_privilege",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Collection<Privilege> privileges;

    @ManyToMany(mappedBy = "roles")
    private Collection<AppUserEntity> users;

    public Role() {
    }

    public Role(String _name) {
        name = _name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
