package org.reggiemcdonald.persistence;

import org.reggiemcdonald.persistence.dto.NumberImageDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberImageRowMapper implements RowMapper<NumberImageDto> {

    @Override
    public NumberImageDto mapRow(ResultSet resultSet, int i) throws SQLException {
        Array array = resultSet.getArray("image_weights");
        Double[][] image_weights = (Double[][]) array.getArray();
        return new NumberImageDto(
                resultSet.getInt("id"),
                resultSet.getInt("session_id"),
                resultSet.getInt("label"),
                (Integer) resultSet.getObject("expected_label"),
                image_weights
        );
    }
}
