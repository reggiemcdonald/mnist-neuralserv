package org.reggiemcdonald.persistence.repo;

import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NumberImageRepository extends CrudRepository<NumberImageEntity, Long> {

    NumberImageEntity findById(long id);

    List<NumberImageEntity> findBySessionId(long sessionId);
}