package org.reggiemcdonald.persistence.service;

import org.reggiemcdonald.exception.TrainingSessionNotFoundException;
import org.reggiemcdonald.persistence.dao.TrainingSessionDao;
import org.reggiemcdonald.persistence.dto.TrainingSessionDto;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class TrainingSessionRepoService implements TrainingSessionService {

    @Resource
    TrainingSessionDao dao;

    @Override
    public List<TrainingSessionDto> getTrainingSessions() {
        return dao.getTrainingSessions();
    }

    @Override
    public List<TrainingSessionDto> findByDate(Date startDate, Date endDate) {
        return dao.findByDate(startDate, endDate);
    }

    @Override
    public TrainingSessionDto findById(int id) throws TrainingSessionNotFoundException {
        return dao.findById(id);
    }
}
