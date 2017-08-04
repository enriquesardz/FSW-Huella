package com.example.ensardz.registrohuella.Datos;

import io.realm.RealmObject;

/**
 * Created by ensardz on 04/08/2017.
 */

public class Professor extends RealmObject {

    private String rawName;
    private String name;
    private String lastName;
    private String title;
    private String userType;
    private String employeeNumber;
    private String fingerPrint;

    public static Professor create(String rawName, String name, String lastName, String title, String userType, String employeeNumber) {
        Professor professor = new Professor();
        professor.rawName = rawName;
        professor.name = name;
        professor.lastName = lastName;
        professor.title = title;
        professor.userType = userType;
        professor.employeeNumber = employeeNumber;
        return professor;
    }

    public String getRawName() {
        return rawName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getUserType() {
        return userType;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }
}
