package org.reggiemcdonald.persistence;

import org.reggiemcdonald.persistence.cols.NumberImageColumns;
import org.reggiemcdonald.persistence.dto.NumberImageDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberImageRowMapper implements RowMapper<NumberImageDto> {

    /**
     * Map a result set row from table number_image to a NumberImageDto
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public NumberImageDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return new NumberImageDto(
                resultSet.getInt(NumberImageColumns.ID.toString()),
                resultSet.getInt(NumberImageColumns.SESSION_ID.toString()),
                resultSet.getInt(NumberImageColumns.LABEL.toString()),
                getOrNullNumber(resultSet, NumberImageColumns.EXPECTED_LABEL.toString()),
                getOrNullArray(resultSet, NumberImageColumns.IMAGE_WEIGHTS.toString())
        );
    }

    /**
     * Gets an SQL.Array at the column name and casts to a 2D double array,
     * or returns null if field is null
     * @param resultSet
     * @param columnLabel
     * @return
     * @throws SQLException
     */
    private Double[][] getOrNullArray(ResultSet resultSet, String columnLabel) throws SQLException {
        Array array = resultSet.getArray(columnLabel);
        return array == null ? null : (Double[][]) array.getArray();
    }

    /**
     * Gets an integer at the given column, or returns null if the field was null
     * @param resultSet
     * @param columnLabel
     * @return
     * @throws SQLException
     */
    private Integer getOrNullNumber(ResultSet resultSet, String columnLabel) throws SQLException {
        Integer i = resultSet.getInt(columnLabel);
        return resultSet.wasNull() ? null : i;
    }
}
