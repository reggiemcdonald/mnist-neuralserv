package org.reggiemcdonald.persistence;

import org.reggiemcdonald.persistence.cols.TrainingSessionCols;
import org.reggiemcdonald.persistence.dto.TrainingSessionDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainingSessionRowMapper implements RowMapper<TrainingSessionDto> {
    @Override
    public TrainingSessionDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return new TrainingSessionDto(
                resultSet.getInt(TrainingSessionCols.ID.toString()),
                resultSet.getInt(TrainingSessionCols.INTERNAL_TRAINING_SIZE.toString()),
                resultSet.getInt(TrainingSessionCols.EXTERNAL_TRAINING_SIZE.toString()),
                resultSet.getInt(TrainingSessionCols.INTERNAL_NUMBER_CORRECT.toString()),
                resultSet.getInt(TrainingSessionCols.EXTERNAL_NUMBER_CORRECT.toString()),
                resultSet.getTimestamp(TrainingSessionCols.TRAINING_DATE.toString())
        );
    }
}
