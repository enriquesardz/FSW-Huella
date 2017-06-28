package com.fime.fsw.huella.huella.data.objetos;

/**
 * Created by ensardz on 27/06/2017.
 */

public class Owner {
    private String employeeNumber,name,fullName,fingerPrint;

    public Owner(){}

    public Owner(String employeeNumber, String name, String fullName, String fingerPrint) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.fullName = fullName;
        this.fingerPrint = fingerPrint;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }
}
