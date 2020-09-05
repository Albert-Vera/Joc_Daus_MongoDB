package com.springboot.mongo_db.repository.Game;


import com.springboot.mongo_db.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerificarDatos {
    public User verificarDatosUser(User user){

        if ( user.getUserName() == null || user.getUserName().length() == 0  ) user.setUserName("ANONIM");

        return user;
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
    public User editarUser(User userOriginal, User user ){
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
}
