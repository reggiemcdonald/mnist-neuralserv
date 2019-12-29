package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.exception.NumberImageNotFoundException;
import org.reggiemcdonald.persistence.dto.NumberImageDto;

import java.util.List;

public interface NumberImageDao {

    List<NumberImageDto> findBySession(int sessionId, int offset);

    NumberImageDto findById(int id) throws NumberImageNotFoundException;

    int insert(int sessionId, int label, Integer expectedLabel, Double[][] imageWeights);


}
