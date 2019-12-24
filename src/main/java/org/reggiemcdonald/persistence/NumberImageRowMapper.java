package org.reggiemcdonald.persistence;

import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberImageRowMapper implements RowMapper<NumberImageDto> {

    @Override
    public NumberImageDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return new NumberImageDto(
                resultSet.getInt("id"),
                resultSet.getInt("label"),
                resultSet.getString("url")
        );
    }
}
