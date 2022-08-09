package com.example.utoken;

public class User {
    String id;
    String nic;
    String vid;
    String password;

    public User(){

    }

    public User(String id, String nic, String vid, String password) {
        this.id = id;
        this.nic = nic;
        this.vid = vid;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getNic() {
        return nic;
    }

    public String getVid() {
        return vid;
    }

    public String getPassword() {
        return password;
    }
}
