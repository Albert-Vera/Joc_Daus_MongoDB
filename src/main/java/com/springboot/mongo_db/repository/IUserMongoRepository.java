package com.springboot.mongo_db.repository;


import com.springboot.mongo_db.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserMongoRepository extends MongoRepository<User, Integer> {
        List<User> findByIdUser(Integer id);

        }
