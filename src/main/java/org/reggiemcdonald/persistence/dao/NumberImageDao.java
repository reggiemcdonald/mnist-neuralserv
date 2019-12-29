package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.exception.NotFoundException;
import org.reggiemcdonald.persistence.dto.NumberImageDto;

public interface NumberImageDao {

    NumberImageDto findById(int id) throws NotFoundException;

    int insert(int sessionId, int label, Integer expectedLabel, Double[][] imageWeights);


}
