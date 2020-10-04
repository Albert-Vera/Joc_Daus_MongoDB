package com.springboot.mongo_db.model;

public class JsonCall {

    private String id;
    private String userName;

    public JsonCall() {
    }

    public JsonCall(String id) {
        this.id = id;
    }

    public JsonCall(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
