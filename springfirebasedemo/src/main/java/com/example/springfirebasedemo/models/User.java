package com.example.springfirebasedemo.models;

public class User {
    private String fisrtname;
    private String lastname;
    private String username;
    private String passwprd;
    private String position;

    public User(){

    }
    public User(String fisrtname, String lastname, String username, String passwprd, String position) {
        this.fisrtname = fisrtname;
        this.lastname = lastname;
        this.username = username;
        this.passwprd = passwprd;
        this.position = position;
    }
    //================================= Getter =================================//

    public String getFisrtname() {
        return fisrtname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswprd() {
        return passwprd;
    }

    public String getPosition() {
        return position;
    }

    //================================= Setter =================================//

    public void setFisrtname(String fisrtname) {
        this.fisrtname = fisrtname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswprd(String passwprd) {
        this.passwprd = passwprd;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
