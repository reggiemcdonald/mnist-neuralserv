package org.reggiemcdonald.persistence.repo;

import org.reggiemcdonald.persistence.entity.TrainingSessionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TrainingSessionRepository  extends CrudRepository<TrainingSessionEntity, Long> {

    @Query("SELECT t FROM TrainingSessionEntity t WHERE t.trainingDate < :end")
    List<TrainingSessionEntity> findAllBeforeDate(@Param("end") Date end);

    @Query("SELECT t FROM TrainingSessionEntity t WHERE t.trainingDate > :start")
    List<TrainingSessionEntity> finaAllAfterDate(@Param("start") Date start);

    List<TrainingSessionEntity> findAllByTrainingDateBetween(Date start, Date end);

}
