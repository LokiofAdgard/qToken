package com.example.utoken;

public class Admin {
    String name;
    String password;

    public Admin() {

    }

    public Admin(String n, String p) {
        this.name = n;
        this.password = p;
    }

    public String getPassword(){
        return this.password;
    }

    public String getName(){
        return this.name;
    }
}
