package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.exception.TrainingSessionNotFoundException;
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
     * @param startDate: The earliest date to include training sessions
     * @param endDate: The latest date to include training sessions
     * @return
     */
    List<TrainingSessionDto> findByDate(Date startDate, Date endDate);

    /**
     * Get a particular training session
     * @param id
     * @return
     */
    TrainingSessionDto findById(int id) throws TrainingSessionNotFoundException;
}
