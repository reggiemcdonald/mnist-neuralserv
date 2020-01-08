package org.reggiemcdonald.persistence.repo;

import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NumberImageRepository extends CrudRepository<NumberImageEntity, Long> {

    List<NumberImageEntity> findAllBySessionId(long sessionId);

    @Query("SELECT n from NumberImageEntity n WHERE n.expectedLabel IS NOT NULL")
    List<NumberImageEntity> findAllWithExpectedLabel();

}