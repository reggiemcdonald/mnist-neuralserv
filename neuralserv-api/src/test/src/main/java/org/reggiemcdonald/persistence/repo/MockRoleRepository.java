package org.reggiemcdonald.persistence.repo;

import org.reggiemcdonald.persistence.entity.Privilege;
import org.reggiemcdonald.persistence.entity.Role;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.reggiemcdonald.util.ListUtils.linkedList;

@TestConfiguration
public class MockRoleRepository {

    private RoleRepository repository;

    private Map<String, Role> roles;

    public MockRoleRepository() {
        roles = new HashMap<>();
        setup();
    }

    public RoleRepository getRepository() {
        return repository;
    }

    public void setup() {
        Privilege read = mockPrivilege(0, "PRIVILEGE_READ");
        Privilege write = mockPrivilege(1, "PRIVILEGE_WRITE");
        Privilege maintain = mockPrivilege(2, "PRIVILEGE_MAINTAIN");
        Role adminRole = mockRole(0L, "ROLE_ADMIN", read, write, maintain);
        Role userRole = mockRole(1L, "ROLE_USER", read, write);
        roles.put("ROLE_ADMIN", adminRole);
        roles.put("ROLE_USER", userRole);

        repository = mock(RoleRepository.class);

        when(repository.findByName(anyString()))
                .thenAnswer(im -> {
                    String name = im.getArgument(0);
                    return roles.get(name);
                });
    }

    private Privilege mockPrivilege(long id, String name) {
        Privilege privilege = mock(Privilege.class);
        when(privilege.getId())
                .thenReturn(id);
        when(privilege.getName())
                .thenReturn(name);
        when(privilege.getAuthority())
                .thenReturn(name);
        return privilege;
    }

    private Role mockRole(long id, String name, Privilege... privileges) {
        Role role = mock(Role.class);
        when(role.getId())
                .thenReturn(id);
        when(role.getName())
                .thenReturn(name);
        when(role.getPrivileges())
                .thenReturn(linkedList(privileges));
        return role;
    }

}
