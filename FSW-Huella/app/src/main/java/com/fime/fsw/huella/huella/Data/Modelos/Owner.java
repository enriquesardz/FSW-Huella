package com.fime.fsw.huella.huella.Data.Modelos;

import io.realm.RealmObject;

/**
 * Created by Quique on 08/07/2017.
 */

public class Owner extends RealmObject{

    //Keys para Owner
    public static final String RAW_NAME_KEY = "rawName";
    public static final String USER_TYPE_KEY = "userType";
    public static final String NAME_KEY = "name";
    public static final String TITLE_KEY = "title";
    public static final String LAST_NAME_KEY = "lastName";
    public static final String FINGERPRINT_KEY = "fingerPrint";
    public static final String EMPLOYEE_NUMBER_KEY = "employeeNumber";

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
