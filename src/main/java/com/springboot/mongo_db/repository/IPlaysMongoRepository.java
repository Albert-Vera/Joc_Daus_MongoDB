package com.springboot.mongo_db.repository;

import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlaysMongoRepository extends MongoRepository<Plays, Integer> {

    List<Plays>  findAllByUserId(Integer id);

}
