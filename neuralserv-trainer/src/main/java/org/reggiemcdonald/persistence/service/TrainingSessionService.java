package org.reggiemcdonald.persistence.service;

import org.reggiemcdonald.persistence.dto.TrainingSessionDto;

import java.util.Date;
import java.util.List;

public interface TrainingSessionService {

    /**
     * Service for getting all the training sessions
     * @return
     */
    List<TrainingSessionDto> getTrainingSessions();

    /**
     * Service for finding all training sessions within a particular date
     * @param trainingDate
     * @return
     */
    List<TrainingSessionDto> findByDate(Date trainingDate);

    /**
     * Service for finding a particular training session
     * @param id
     * @return
     */
    TrainingSessionDto findById(int id);
}
