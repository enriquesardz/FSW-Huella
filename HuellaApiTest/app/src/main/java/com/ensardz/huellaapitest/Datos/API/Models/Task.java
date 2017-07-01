package com.ensardz.huellaapitest.Datos.API.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Quique on 29/06/2017.
 */

public class Task {
    private String id;
    private String room;
    @SerializedName("assignment")
    private String assignment;
    private String academyHour;
    private String barcode;
    private String employeeNumber;
    private String name;
    private String fullName;
    private String hexCode;

    public Task(String id, String room, String assignment, String academyHour, String barcode, String employeeNumber, String name, String fullName, String hexCode) {
        this.id = id;
        this.room = room;
        this.assignment = assignment;
        this.academyHour = academyHour;
        this.barcode = barcode;
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.fullName = fullName;
        this.hexCode = hexCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getAcademyHour() {
        return academyHour;
    }

    public void setAcademyHour(String academyHour) {
        this.academyHour = academyHour;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", room='" + room + '\'' +
                ", assignment='" + assignment + '\'' +
                ", academyHour='" + academyHour + '\'' +
                ", barcode='" + barcode + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", hexCode='" + hexCode + '\'' +
                '}';
    }
}
