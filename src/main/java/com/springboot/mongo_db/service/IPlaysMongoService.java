package com.springboot.mongo_db.service;


import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.repository.IPlaysMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPlaysMongoService {
    List<Plays> findAllByUserId(Integer id);
    int getDiceNumberRandom();
    Plays rollDice(Plays plays, int idUser);
}
