package com.adriancasado.endevinanumero;

public class Record {
    private String user;
    private int tries;

    // Constructor
    public Record(String user, int tries) {
        this.user = user;
        this.tries = tries;
    }

    // Getters
    public String getUser() {
        return user;
    }

    public int getTries() {
        return tries;
    }
}