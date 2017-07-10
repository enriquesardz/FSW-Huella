package com.fime.fsw.huella.huella.Data.Modelos;

import io.realm.RealmObject;

/**
 * Created by Quique on 08/07/2017.
 */

public class Owner extends RealmObject{
    private long _id;
    private String employeeNumber;
    private String type;
    private String name;
    private String fullName;
    private String title;
    private String shortTitle;
    private String fingerPrint;

    public Owner(){}

    public Owner create(long _id, String employeeNumber, String type, String name, String fullName, String title, String shortTitle, String fingerPrint) {
        Owner owner = new Owner();
        owner._id = _id;
        owner.employeeNumber = employeeNumber;
        owner.type = type;
        owner.name = name;
        owner.fullName = fullName;
        owner.title = title;
        owner.shortTitle = shortTitle;
        owner.fingerPrint = fingerPrint;
        return owner;
    }
}
