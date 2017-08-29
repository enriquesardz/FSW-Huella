package com.fime.fsw.huella.huella.Data.Modelos.RealmObjects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quique on 08/07/2017.
 */

public class Owner extends RealmObject{

    //Keys para Owner
    public static final String RAW_NAME_KEY = "ownerRawName";
    public static final String USER_TYPE_KEY = "ownerUserType";
    public static final String NAME_KEY = "ownerName";
    public static final String TITLE_KEY = "ownerTitle";
    public static final String LAST_NAME_KEY = "ownerLastName";

    @PrimaryKey
    private String rawName;
    private String userType;
    private String name;
    private String title;
    private String lastName;
    private String fingerPrint;
    private String employeeNumber;

    public Owner(){}

    public static Owner create(String rawName, String userType, String name, String title, String lastName, String fingerPrint, String employeeNumber) {
        Owner owner = new Owner();
        owner.rawName = rawName;
        owner.userType = userType;
        owner.name = name;
        owner.title = title;
        owner.lastName = lastName;
        owner.fingerPrint = fingerPrint;
        owner.employeeNumber = employeeNumber;
        return owner;
    }

    public String getRawName() {
        return rawName;
    }

    public String getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "rawName='" + rawName + '\'' +
                ", userType='" + userType + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fingerPrint='" + fingerPrint + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                '}';
    }
}
