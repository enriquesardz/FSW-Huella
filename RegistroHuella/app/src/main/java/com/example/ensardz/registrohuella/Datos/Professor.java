package com.example.ensardz.registrohuella.Datos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ensardz on 04/08/2017.
 */

public class Professor extends RealmObject {

    public static final String RAW_NAME_FIELD = "rawName";
    public static final String FINGER_PRINT_FIELD = "fingerPrint";

    public static final String RAW_NAME_KEY =  "professorRawName";

    @PrimaryKey
    private String rawName;
    private String employeeNumber;
    private String fingerPrint;
    private boolean wasUploaded;

    public static Professor create(String rawName, String employeeNumber, String fingerPrint) {
        Professor professor = new Professor();
        professor.rawName = rawName;
        professor.employeeNumber = employeeNumber;
        professor.fingerPrint = fingerPrint;
        professor.wasUploaded = false;
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

    @Override
    public String toString() {
        return "Professor{" +
                "rawName='" + rawName + '\'' +
                '}';
    }
}
