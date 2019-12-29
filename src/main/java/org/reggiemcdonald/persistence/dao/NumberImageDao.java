package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.exception.NumberImageNotFoundException;
import org.reggiemcdonald.persistence.dto.NumberImageDto;

import java.util.List;

public interface NumberImageDao {

    /**
     * Returns a page of number image dtos that belong to a session
     * @param sessionId
     * @param offset
     * @return
     */
    List<NumberImageDto> findBySession(int sessionId, int offset);

    /**
     * Get a number image dto by its id
     * @param id
     * @return
     * @throws NumberImageNotFoundException
     */
    NumberImageDto findById(int id) throws NumberImageNotFoundException;

    /**
     * Persist a number image
     * @param sessionId
     * @param label
     * @param expectedLabel
     * @param imageWeights
     * @return
     */
    int insert(int sessionId, int label, Integer expectedLabel, Double[][] imageWeights);


}
