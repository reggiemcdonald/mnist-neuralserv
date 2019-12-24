package org.reggiemcdonald.persistence.dao;

import org.reggiemcdonald.persistence.NumberImageDto;

public interface NumberImageDao {

    NumberImageDto findById(int id);

    int insert(NumberImageDto dto);


}
