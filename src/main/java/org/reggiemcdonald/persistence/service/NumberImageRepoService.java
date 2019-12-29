package org.reggiemcdonald.persistence.service;

import org.reggiemcdonald.exception.NumberImageNotFoundException;
import org.reggiemcdonald.persistence.dao.NumberImageDao;
import org.reggiemcdonald.persistence.dto.NumberImageDto;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class NumberImageRepoService implements NumberImageService {

    @Resource
    NumberImageDao dao;

    @Override
    public List<NumberImageDto> findBySession(int sessionId, int offset) {
        return dao.findBySession(sessionId, offset);
    }

    @Override
    public NumberImageDto findById(int id) throws NumberImageNotFoundException {
        return dao.findById(id);
    }

    @Override
    public int insert(int sessionId, int label, Integer expectedLabel, Double[][] imageWeights) {
        return dao.insert(sessionId, label, expectedLabel, imageWeights);
    }
}
