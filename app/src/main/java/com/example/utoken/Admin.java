package com.example.utoken;

public class Admin {
    String name;
    String password;
    Integer petrol;
    Integer diesel;
    String id;
    String location;

    public Admin() {

    }

    public Admin(String id, String n, String p) {
        this.name = n;
        this.password = p;
    }

    public String getPassword(){
        return this.password;
    }

    public String getName(){
        return this.name;
    }

    public String getId() { return this.id;}

    public int getPetrol() {return this.petrol;}

    public int getDiesel() {return this.diesel;}

    public String getLocation(){return this.location;}

    public void setLocation(String l){this.location = l;}

    public Integer check(String fuel){
        if (fuel.contains("p")) return petrol;
        else return diesel;
    }

    public void reduce(String f, Integer v) {
        if (f.contains("p")) petrol = petrol - v;
        else diesel = diesel - v;
    }

    public void setFuel(String f, Integer v){
        if (f.contains("p")) petrol = v;
        else diesel = v;
    }
}
