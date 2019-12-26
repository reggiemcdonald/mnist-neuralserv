package org.reggiemcdonald.persistence.service;

import org.reggiemcdonald.exception.NotFoundException;
import org.reggiemcdonald.persistence.NumberImageDto;

public interface NumberImageService {

    NumberImageDto findById(int id) throws NotFoundException;

    int insert(int label, double[][] imageWeights);
}
