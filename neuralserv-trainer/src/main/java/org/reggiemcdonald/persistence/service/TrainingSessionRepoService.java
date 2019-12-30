package org.reggiemcdonald.persistence.service;

import org.reggiemcdonald.persistence.dao.TrainingSessionDao;
import org.reggiemcdonald.persistence.dto.TrainingSessionDto;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class TrainingSessionRepoService implements TrainingSessionService {

    @Resource
    TrainingSessionDao dao;

    @Override
    public List<TrainingSessionDto> getTrainingSessions() {
        return dao.getTrainingSessions();
    }

    @Override
    public List<TrainingSessionDto> findByDate(Date trainingDate) {
        return dao.findByDate(trainingDate);
    }

    @Override
    public TrainingSessionDto findById(int id) {
        return dao.findById(id);
    }
}
