package org.reggiemcdonald.persistence;

import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.reggiemcdonald.persistence.entity.Privilege;
import org.reggiemcdonald.persistence.entity.Role;
import org.reggiemcdonald.persistence.repo.AppUserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.reggiemcdonald.util.ListUtils.linkedList;

@TestConfiguration
public class MockAppUserRepository {

    private String adminName = "Test Admin", userName = "Test User";
    private String adminPassword = "admin pass", userPassword = "userPass";

    private AppUserEntity admin;
    private AppUserEntity user;

    @MockBean
    private AppUserRepository repository;

    public MockAppUserRepository() {
        setup();
    }

    public AppUserRepository getRepository() {
        return repository;
    }

    private void setup() {
        Privilege read = mockPrivilege(0, "PRIVILEGE_READ");
        Privilege write = mockPrivilege(1, "PRIVILEGE_WRITE");
        Privilege maintain = mockPrivilege(2, "PRIVILEGE_MAINTAIN");
        Role adminRole = mockRole(0L, "ROLE_ADMIN", read, write, maintain);
        Role userRole = mockRole(1L, "ROLE_USER", read, write);
        admin = mockUser(0, adminName, adminPassword, adminRole);
        user = mockUser(1, userName, userPassword, userRole);

        when(repository.findAll())
                .thenReturn(linkedList(admin, user));
        when(repository.findByUsername(adminName))
                .thenReturn(admin);
        when(repository.findByUsername(userName))
                .thenReturn(user);

    }

    private Privilege mockPrivilege(long id, String name) {
        Privilege privilege = mock(Privilege.class);
        when(privilege.getId())
                .thenReturn(id);
        when(privilege.getName())
                .thenReturn(name);
        when(privilege.getAuthority())
                .thenReturn(privilege.getName());
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

    private AppUserEntity mockUser(long id, String name, String password, Role... roles) {
        AppUserEntity entity = mock(AppUserEntity.class);
        when(entity.getId())
                .thenReturn(id);
        when(entity.getUsername())
                .thenReturn(name);
        when(entity.getPassword())
                .thenReturn(password);
        when(entity.getRoles())
                .thenReturn(linkedList(roles));
        return entity;
    }
}
