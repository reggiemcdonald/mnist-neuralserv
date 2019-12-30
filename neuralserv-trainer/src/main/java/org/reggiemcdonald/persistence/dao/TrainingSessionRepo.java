package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.persistence.cols.TrainingSessionCols;
import org.reggiemcdonald.persistence.dto.TrainingSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Date;
import java.util.List;

public class TrainingSessionRepo implements TrainingSessionDao {

    @Autowired
    private NamedParameterJdbcTemplate template;

    // COLS
    private String ID = TrainingSessionCols.ID.toString();
    private String TRAINING_DATE = TrainingSessionCols.TRAINING_DATE.toString();

    // SQL
    private String GET = "SELECT * FROM training_session";
    private String FIND_BY_ID = String.format("SELECT * FROM training_session WHERE %s=:%s", ID, ID);
    private String FIND_BY_DATE = String.format("SELECT * FROM training_session WHERE %s BETWEEN :start AND :end", TRAINING_DATE);

    @Override
    public List<TrainingSessionDto> getTrainingSessions() {
        return null; // TODO
    }

    @Override
    public List<TrainingSessionDto> findByDate(Date trainingDate) {
        return null; // TODO
    }

    @Override
    public TrainingSessionDto findById(int id) {
        return null; // TODO
    }
}
