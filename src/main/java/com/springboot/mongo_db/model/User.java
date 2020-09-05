package com.springboot.mongo_db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Document(collection="user")
public class User implements Serializable {

    @Id
    private int idUser;

    private String userName;

    private int counterPlays;

    private double ranking;

    private int playsWon;

    private String dateRegister;

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @JsonIgnore
    private List<Plays> playsList ;

    public User() {
    }
    public User(int idUser, String userName, int counterPlays, double ranking, int playsWon, List<Plays> playsList, String dateRegister) {
        this.idUser = idUser;
        this.userName = userName;
        this.counterPlays = counterPlays;
        this.ranking = ranking;
        this.playsWon = playsWon;
        this.playsList = playsList;
        this.dateRegister = dateRegister;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public List<Plays> getPlaysList() {
        return playsList;
    }

    public void setPlaysList(Plays play) {
        if(this.playsList == null){
            this.playsList = new ArrayList<>();
        }
        this.playsList.add(play);
    }

    public static String getSequenceName() {
        return SEQUENCE_NAME;
    }

    public int getCounterPlays() {
        return counterPlays;
    }

    public void setCounterPlays(int counterPlays) {
        this.counterPlays = counterPlays;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public int getPlaysWon() {
        return playsWon;
    }

    public void setPlaysWon(int playsWon) {
        this.playsWon = playsWon;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
