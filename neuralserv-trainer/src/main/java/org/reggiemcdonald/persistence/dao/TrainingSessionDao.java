package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.persistence.dto.TrainingSessionDto;

import java.util.Date;
import java.util.List;

public interface TrainingSessionDao {

    /**
     * Get all training sessions
     * @return
     */
    List<TrainingSessionDto> getTrainingSessions();

    /**
     * Get all training sessions between a particular date
     * @param trainingDate
     * @return
     */
    List<TrainingSessionDto> findByDate(Date trainingDate);

    /**
     * Get a particular training session
     * @param id
     * @return
     */
    TrainingSessionDto findById(int id);
}
