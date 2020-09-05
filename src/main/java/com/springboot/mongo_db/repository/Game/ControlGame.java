package com.springboot.mongo_db.repository.Game;


import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.model.User;

import java.text.DecimalFormat;
import java.util.List;

public class ControlGame {
    public Plays rollDice(Plays plays, int idUser){

        plays.setDiceOne( getDiceNumberRandom());
        plays.setDiceTwo( getDiceNumberRandom());

        if ( plays.getDiceOne() + plays.getDiceTwo() == 7) {
            plays.setWin(true);
        }else plays.setWin(false);
        plays.setUserId(idUser);
        return plays;
    }
    private int getDiceNumberRandom(){
        return (int) (Math.random() * 6 + 1);
    }

    public User actualizarMarcadorUser(Plays plays, List<User> user){
        // setPlayWon
        if ( plays.getDiceOne() + plays.getDiceTwo() == 7) {
            user.get(0).setPlaysWon( user.get(0).getPlaysWon()+1);
        }
        //set Counter Plays
        user.get(0).setCounterPlays( user.get(0).getCounterPlays()+1);
        // set Ranking
        DecimalFormat formatter = new DecimalFormat("###,##");
        double numero =((double)user.get(0).getPlaysWon() / (double)user.get(0).getCounterPlays() *100);
        user.get(0).setRanking(Double.parseDouble(formatter.format(numero)));
        return user.get(0);
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
