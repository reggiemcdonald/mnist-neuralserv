package org.reggiemcdonald.persistence.service;

import org.reggiemcdonald.persistence.NumberImageDto;

public interface NumberImageService {

    NumberImageDto findById(int id);

    int insert(int label);
}
