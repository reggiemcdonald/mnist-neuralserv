package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.exception.NumberImageNotFoundException;
import org.reggiemcdonald.persistence.cols.NumberImageColumns;
import org.reggiemcdonald.persistence.dto.NumberImageDto;
import org.reggiemcdonald.persistence.NumberImageRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NumberImageRepo implements NumberImageDao {

    NamedParameterJdbcTemplate template;
    JdbcTemplate jdbcTemplate;

    // COLUMNS
    private final String ID = NumberImageColumns.ID.toString();
    private final String SESSION_ID = NumberImageColumns.SESSION_ID.toString();
    private final String LABEL = NumberImageColumns.LABEL.toString();
    private final String IMAGE_WEIGHTS = NumberImageColumns.IMAGE_WEIGHTS.toString();
    private final String EXPECTED_LABEL = NumberImageColumns.EXPECTED_LABEL.toString();

    // QUERY PARAMS
    private final String LIMIT = "limit";
    private final String OFFSET = "offset";

    private final int PAGE_LIMIT = 100;

    // SQL
    private final String INSERT = String.format("INSERT INTO number_image(%s, %s, %s, %s) VALUES(:%s, :%s, :%s, :%s)",
            SESSION_ID, LABEL, EXPECTED_LABEL, IMAGE_WEIGHTS, SESSION_ID, LABEL, EXPECTED_LABEL, IMAGE_WEIGHTS);
    private final String FIND_BY_ID = String.format("SELECT * from number_image WHERE %s=:%s", ID, ID);
    private final String FIND_BY_SESSION_ID = String.format("SELECT * FROM number_image WHERE %s=:%s LIMIT :%s OFFSET :%s",
            SESSION_ID, SESSION_ID, LIMIT, OFFSET);

    @Autowired
    public NumberImageRepo(NamedParameterJdbcTemplate _template, JdbcTemplate _jdbcTemplate) {
        template = _template;
        jdbcTemplate = _jdbcTemplate;
    }

    @Override
    public List<NumberImageDto> findBySession(int sessionId, int page) {
        int offset = PAGE_LIMIT * page;
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(SESSION_ID, sessionId)
                .addValue(LIMIT, PAGE_LIMIT)
                .addValue(OFFSET, offset);
        List<NumberImageDto> list = template.query(FIND_BY_SESSION_ID, paramSource, new NumberImageRowMapper());
        return list;
    }

    @Override
    public NumberImageDto findById(int id) throws NumberImageNotFoundException {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, id);
        List<NumberImageDto> list = template.query(FIND_BY_ID, paramSource, new NumberImageRowMapper());
        if (list.isEmpty())
            throw new NumberImageNotFoundException(id);
        return list.iterator().next();
    }

    @Override
    public int insert(int sessionId, int label, Integer expectedLabel, Double[][] imageWeights) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(SESSION_ID, sessionId)
                .addValue(LABEL, label)
                .addValue(EXPECTED_LABEL, expectedLabel)
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
