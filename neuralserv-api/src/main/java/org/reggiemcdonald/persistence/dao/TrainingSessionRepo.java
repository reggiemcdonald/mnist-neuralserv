package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.exception.TrainingSessionNotFoundException;
import org.reggiemcdonald.persistence.TrainingSessionRowMapper;
import org.reggiemcdonald.persistence.cols.TrainingSessionCols;
import org.reggiemcdonald.persistence.dto.TrainingSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
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
        return template.query(GET, new TrainingSessionRowMapper());
    }

    @Override
    public List<TrainingSessionDto> findByDate(Date startDate, Date endDate) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("start", startDate)
                .addValue("end", endDate);
        return template.query(FIND_BY_DATE, paramSource, new TrainingSessionRowMapper());
    }

    @Override
    public TrainingSessionDto findById(int id) throws TrainingSessionNotFoundException {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, id);
        List<TrainingSessionDto> dtos = template.query(FIND_BY_ID, paramSource, new TrainingSessionRowMapper());
        if (dtos.isEmpty())
            throw new TrainingSessionNotFoundException(id);
        return dtos.iterator().next();
    }
}
