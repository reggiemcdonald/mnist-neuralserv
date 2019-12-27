package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.exception.NotFoundException;
import org.reggiemcdonald.persistence.NumberImageDto;
import org.reggiemcdonald.persistence.NumberImageRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.SqlValue;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class NumberImageRepo implements NumberImageDao {

    NamedParameterJdbcTemplate template;
    JdbcTemplate jdbcTemplate;

    public final String INSERT = "INSERT INTO number_image(label, image_weights) VALUES(:label, :image_weights)";
    public final String FIND = "SELECT * from number_image WHERE id=:id";
    private final String ID = "id";
    private final String LABEL = "label";
    private final String IMAGE_WEIGHTS = "image_weights";
    private final String EXECTED_LABEL = "expected_label";

    @Autowired
    public NumberImageRepo(NamedParameterJdbcTemplate _template, JdbcTemplate _jdbcTemplate) {
        template = _template;
        jdbcTemplate = _jdbcTemplate;
    }

    @Override
    public NumberImageDto findById(int id) throws NotFoundException {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, id);
        List<NumberImageDto> list = template.query(FIND, paramSource, new NumberImageRowMapper());
        if (list.isEmpty())
            throw new NotFoundException(id);
        return list.iterator().next();
    }

    @Override
    public int insert(int label, Integer expectedLabel, Double[][] imageWeights) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(LABEL, label)
                .addValue(EXECTED_LABEL, expectedLabel)
                .addValue(IMAGE_WEIGHTS, createArrayOf(imageWeights));
        template.update(INSERT, paramSource, keyHolder, new String[] { "id" });
        return keyHolder.getKey().intValue();
    }

    private Array createArrayOf(Double[][] darr) {
        try {
            Array sqlArray = jdbcTemplate
                    .getDataSource()
                    .getConnection()
                    .createArrayOf("float8", darr);
            return sqlArray;
        } catch (SQLException e) {
            return null;
        }
    }
}
