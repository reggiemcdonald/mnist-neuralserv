package org.reggiemcdonald.persistence.repo;

import org.reggiemcdonald.persistence.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
