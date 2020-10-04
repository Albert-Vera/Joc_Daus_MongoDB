package com.springboot.mongo_db.service;

import com.springboot.mongo_db.model.JsonCall;
import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.model.User;
import com.springboot.mongo_db.repository.IUserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserMongoService implements IUserMongoService {

    @Qualifier("IUserMongoRepository")
    @Autowired
    IUserMongoRepository repoUser;



    public String verificarUserName(String name){

        if ( name == null || name.length() == 0  ) name = "ANONIM" ;
        return name;
    }
    public User asignarValoresUser(User user, String str){
        user.setCounterPlays(0);
        user.setPlaysWon(0);
        user.setRanking(0);
        // condicional para conservar la DateRegister al borrar Lista de Plays
        if ( !str.equalsIgnoreCase("delete")) user.setDateRegister(getDate());
        else user.setDateRegister(user.getDateRegister());
        return user;
    }
    public User editarUser(User userOriginal, User user, JsonCall jsonCall){
        user.setIdUser(Integer.parseInt(jsonCall.getId()));
        user.setUserName(jsonCall.getUserName());
        user.setCounterPlays(userOriginal.getCounterPlays());
        user.setPlaysWon(userOriginal.getPlaysWon());
        user.setRanking(userOriginal.getRanking());
        user.setDateRegister(userOriginal.getDateRegister());

        return user;
    }
    public String getDate() {
        Date date = new Date();
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return fecha.format(date);
    }
    public HashMap<String,Object> verifyIds(String ids, HashMap<String, Object> map){
        int id;
        try{
            id = Integer.parseInt( ids );

        }catch (Exception e){
            map.put("success", false);
            map.put("message", "Incorrect data Id : " + ids);
        }
        return map;
    }
    public User actualizarMarcadorUser(Plays plays, User user){
        // setPlayWon
        if ( plays.getDiceOne() + plays.getDiceTwo() == 7) {
            user.setPlaysWon( user.getPlaysWon()+1);
        }
        //set Counter Plays
        user.setCounterPlays( user.getCounterPlays()+1);
        // set Ranking
        DecimalFormat formatter = new DecimalFormat("###,##");
        double numero =((double)user.getPlaysWon() / (double)user.getCounterPlays() *100);
        user.setRanking(Double.parseDouble(formatter.format(numero)));
        return user;
    }
    public int calcularRankingTotal(List<User> userList){
        int mitgaTotal = 0;

        for ( User x: userList){
            mitgaTotal += x.getRanking()/userList.size();
        }
        return mitgaTotal;
    }
    public User rankingLoserWinner(List<User> userList, String str){
        double mesAlt = 0;
        double mesBaix = Integer.MAX_VALUE;
        int idMes= 0;
        int idMenys = 0;

        for ( int i=0; i < userList.size(); i ++){
            if ( userList.get(i).getRanking() > mesAlt) {
                mesAlt = userList.get(i).getRanking();
                idMes = i;
            }
            if ( userList.get(i).getRanking() < mesBaix) {
                mesBaix = userList.get(i).getRanking();
                idMenys = i;
            }
        }
        if (str.equalsIgnoreCase("max")) return userList.get(idMes);
        if (str.equalsIgnoreCase("min")) return userList.get(idMenys);
        return null;
    }
}
