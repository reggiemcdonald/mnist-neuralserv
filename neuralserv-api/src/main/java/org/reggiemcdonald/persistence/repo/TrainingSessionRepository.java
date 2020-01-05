package org.reggiemcdonald.persistence.repo;

import org.reggiemcdonald.persistence.entity.TrainingSessionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TrainingSessionRepository  extends CrudRepository<TrainingSessionEntity, Long> {

    List<TrainingSessionEntity> findAllByTrainingDateBetween(Date start, Date end);
        
}
