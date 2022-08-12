package com.example.utoken;

public class User {
    String id;
    String nic;
    String vid;
    String password;
    Boolean approved;
    Boolean qr;
    Long time;

    public User(){

    }

    public User(String id, String nic, String vid, String password) {
        this.id = id;
        this.nic = nic;
        this.vid = vid;
        this.password = password;
        this.approved = true;
        this.qr = false;
        this.time = Long.valueOf(0);
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

    public Boolean getApproved() { return approved;}

    public Boolean getQr() { return qr;}

    public Long getTime() { return time;}

}
