package com.example.ensardz.registrohuella.Datos;

import io.realm.RealmObject;

/**
 * Created by ensardz on 04/08/2017.
 */

public class Professor extends RealmObject {

    private String rawName;
    private String employeeNumber;
    private String fingerPrint;

    public static Professor create(String rawName, String employeeNumber, String fingerPrint) {
        Professor professor = new Professor();
        professor.rawName = rawName;
        professor.employeeNumber = employeeNumber;
        professor.fingerPrint = fingerPrint;
        return professor;
    }

    public String getRawName() {
        return rawName;
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

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
