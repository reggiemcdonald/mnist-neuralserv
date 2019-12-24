package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.persistence.NumberImageDto;
import org.reggiemcdonald.persistence.NumberImageRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class NumberImageRepo implements NumberImageDao {

    NamedParameterJdbcTemplate template;

    public final String INSERT = "INSERT INTO number_image(label) VALUES(:label)";
    public final String FIND = "SELECT * from number_image WHERE id=:id";
    private final String ID = "id";
    private final String LABEL = "label";
    private final String URL = "url";

    @Autowired
    public NumberImageRepo(NamedParameterJdbcTemplate _template) {
        template = _template;
    }

    @Override
    public NumberImageDto findById(int id) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, id);
        return template.query(FIND, paramSource, new NumberImageRowMapper()).iterator().next();
    }

    @Override
    public int insert(int label) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(LABEL, label);
        template.update(INSERT, paramSource, keyHolder, new String[] { "id" });
        return keyHolder.getKey().intValue();
    }
}
