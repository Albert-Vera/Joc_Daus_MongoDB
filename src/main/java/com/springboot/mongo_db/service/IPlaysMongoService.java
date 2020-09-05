package com.springboot.mongo_db.service;


import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.repository.IPlaysMongoRepository;

import java.util.List;

public interface IPlaysMongoService extends IPlaysMongoRepository {
    List<Plays> findAllByUserId(Integer id);
}
