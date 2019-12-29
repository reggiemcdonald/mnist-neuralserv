package org.reggiemcdonald.persistence;

import org.reggiemcdonald.persistence.dto.NumberImageDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberImageRowMapper implements RowMapper<NumberImageDto> {

    @Override
    public NumberImageDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return new NumberImageDto(
                resultSet.getInt("id"),
                resultSet.getInt("session_id"),
                resultSet.getInt("label"),
                getOrNullNumber(resultSet, "expected_label"),
                getOrNullArray(resultSet, "image_weights")
        );
    }

    public Double[][] getOrNullArray(ResultSet resultSet, String columnLabel) throws SQLException {
        Array array = resultSet.getArray(columnLabel);
        return array == null ? null : (Double[][]) array.getArray();
    }

    public Integer getOrNullNumber(ResultSet resultSet, String columnLabel) throws SQLException {
        Integer i = resultSet.getInt(columnLabel);
        return resultSet.wasNull() ? null : i;
    }
}
