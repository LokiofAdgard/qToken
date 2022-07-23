package com.example.utoken;

public class User {
    String id;
    String nic;
    String password;

    public User(){

    }

    public User(String id, String nic, String password) {
        this.id = id;
        this.nic = nic;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getNic() {
        return nic;
    }

    public String getPassword() {
        return password;
    }
}
