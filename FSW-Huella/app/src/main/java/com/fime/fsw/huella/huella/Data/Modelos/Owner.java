package com.fime.fsw.huella.huella.Data.Modelos;

import io.realm.RealmObject;

/**
 * Created by Quique on 08/07/2017.
 */

public class Owner extends RealmObject{

    private String employeeNumber;
    private String employeeType;
    private String employeeName;
    private String employeeFullName;
    private String fingerPrint;

    public Owner(){}

    public static Owner create(String employeeNumber, String employeeType, String employeeName, String employeeFullName, String fingerPrint) {
        Owner owner = new Owner();
        owner.employeeNumber = employeeNumber;
        owner.employeeType = employeeType;
        owner.employeeName = employeeName;
        owner.employeeFullName = employeeFullName;
        owner.fingerPrint = fingerPrint;
        return owner;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "employeeNumber='" + employeeNumber + '\'' +
                ", employeeType='" + employeeType + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeFullName='" + employeeFullName + '\'' +
                ", fingerPrint='" + fingerPrint + '\'' +
                '}';
    }
}
