package com.fime.fsw.huella.huella.data.objetos;

/**
 * Created by ensardz on 27/06/2017.
 */

public class Target {
    private String room,academyHour;
    private Owner owner;

    public Target(){}

    public Target(String room, String academyHour, Owner owner){
        this.room = room;
        this.academyHour = academyHour;
        this.owner = owner;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setAcademyHour(String academyHour) {
        this.academyHour = academyHour;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getRoom() {
        return room;
    }

    public String getAcademyHour() {
        return academyHour;
    }

    public Owner getOwner() {
        return owner;
    }
}
