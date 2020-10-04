package com.springboot.mongo_db.service;

import com.springboot.mongo_db.model.JsonCall;
import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.model.User;
import com.springboot.mongo_db.repository.IUserMongoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public interface IUserMongoService {

    public String verificarUserName(String name);
    User asignarValoresUser(User user, String str);
    User editarUser(User userOriginal, User user, JsonCall jsonCall);
    String getDate();
    HashMap<String,Object> verifyIds(String ids, HashMap<String, Object> map);
    User actualizarMarcadorUser(Plays plays, User user);
    int calcularRankingTotal(List<User> userList);
    User rankingLoserWinner(List<User> userList, String str);

}
