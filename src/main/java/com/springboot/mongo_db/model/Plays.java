package com.springboot.mongo_db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;




@Document(collection="plays")
public class Plays {
    @Id
    private int idPlay;
    private int diceOne ;
    private int diceTwo;
    private boolean win;
    private int userId;
    @Transient
    public static final String SEQUENCE_PLAYS = "play_sequence";
    @JsonIgnore
    User userMany;

    public Plays() {
    }

    public int getIdPlay() {
        return idPlay;
    }

    public void setIdPlay(int idPlay) {
        this.idPlay = idPlay;
    }

    public int getDiceOne() {
        return diceOne;
    }

    public void setDiceOne(int diceOne) {
        this.diceOne = diceOne;
    }

    public int getDiceTwo() {
        return diceTwo;
    }

    public void setDiceTwo(int diceTwo) {
        this.diceTwo = diceTwo;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public User getUserMany() {
        return userMany;
    }

    public void setUserMany(User userMany) {
        this.userMany = userMany;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
