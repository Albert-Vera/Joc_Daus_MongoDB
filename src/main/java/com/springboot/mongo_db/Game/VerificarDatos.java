package com.springboot.mongo_db.Game;


import com.springboot.mongo_db.model.JsonCall;
import com.springboot.mongo_db.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class VerificarDatos {
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
}
