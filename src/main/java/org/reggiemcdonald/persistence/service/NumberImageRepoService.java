package org.reggiemcdonald.persistence.service;

import org.reggiemcdonald.exception.NotFoundException;
import org.reggiemcdonald.persistence.dao.NumberImageDao;
import org.reggiemcdonald.persistence.NumberImageDto;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NumberImageRepoService implements NumberImageService {

    @Resource
    NumberImageDao dao;

    @Override
    public NumberImageDto findById(int id) throws NotFoundException {
        return dao.findById(id);
    }

    @Override
    public int insert(int label, Integer expectedLabel, Double[][] imageWeights) {
        return dao.insert(label, expectedLabel, imageWeights);
    }
}
