package org.reggiemcdonald.persistence.repo;

import org.junit.runner.RunWith;
import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.reggiemcdonald.persistence.entity.Role;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.reggiemcdonald.util.ListUtils.linkedList;

@TestConfiguration
public class MockAppUserRepository {

    private Map<String, AppUserEntity> users;
    private Random random;

    private AppUserRepository repository;

    public MockAppUserRepository() {
        users = new HashMap<>();
        random = new Random();
        repository = mock(AppUserRepository.class);
        setup();
    }

    public AppUserRepository getRepository() {
        return repository;
    }

    public AppUserEntity get(String name) {
        return users.get(name);
    }

    private void setup() {


        when(repository.findAll())
                .thenReturn(users.values());
        when(repository.findByUsername(anyString()))
                .thenAnswer((im) -> {
                    String username = im.getArgument(0);
                    return users.get(username);
                });
        when(repository.save(any(AppUserEntity.class)))
                .thenAnswer(im -> {
                    AppUserEntity appUserEntity = im.getArgument(0);
                    String name = appUserEntity.getUsername();
                    String password = appUserEntity.getPassword();
                    long id = random.nextLong() * 1000;
                    Collection<Role> roles = appUserEntity.getRoles();
                    Role[] arr = roles.toArray(new Role[0]);
                    AppUserEntity mockUser = mockUser(id, name, password, arr);
                    users.put(name, mockUser);
                    return mockUser;
                });

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
