package com.ensardz.huellaapitest.Datos.API.Models;

import com.ensardz.huellaapitest.HuellaApiTestApp;
import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quique on 29/06/2017.
 */

public class Task extends RealmObject{
    @PrimaryKey
    private int id;
    private String _id;
    private String period;
    private String academyHour;
    private String group;
    private String language;
    private String day;
    private String modality;

    //Owner
    @SerializedName("_id")
    private String ownerId;
    @SerializedName("rawName")
    private String ownerRawName;
    @SerializedName("userType")
    private String ownerUserType;
    @SerializedName("name")
    private String ownerName;
    @SerializedName("lastName")
    private String ownerLastName;
    @SerializedName("fingerPrint")
    private String ownerFingerPrint;
    @SerializedName("employeeNumber")
    private String ownerEmployeeNumber;

    //Assignment
    @SerializedName("_id")
    private String assignmentId;
    @SerializedName("rawName")
    private String assignmentRawName;
    @SerializedName("code")
    private String assignmentCode;
    @SerializedName("name")
    private String assignmentName;
    @SerializedName("plan")
    private String assignmentPlan;

    public Task(){}

    public static Task create(String _id, String period, String academyHour, String group, String language, String day, String modality, String ownerId, String ownerRawName, String ownerUserType, String ownerName, String ownerLastName, String ownerFingerPrint, String ownerEmployeeNumber, String assignmentId, String assignmentRawName, String assignmentCode, String assignmentName, String assignmentPlan) {
        Task task = new Task();
        task.id = HuellaApiTestApp.TaskID.incrementAndGet();
        task._id = _id;
        task.period = period;
        task.academyHour = academyHour;
        task.group = group;
        task.language = language;
        task.day = day;
        task.modality = modality;
        task.ownerId = ownerId;
        task.ownerRawName = ownerRawName;
        task.ownerUserType = ownerUserType;
        task.ownerName = ownerName;
        task.ownerLastName = ownerLastName;
        task.ownerFingerPrint = ownerFingerPrint;
        task.ownerEmployeeNumber = ownerEmployeeNumber;
        task.assignmentId = assignmentId;
        task.assignmentRawName = assignmentRawName;
        task.assignmentCode = assignmentCode;
        task.assignmentName = assignmentName;
        task.assignmentPlan = assignmentPlan;
        return task;
    }

    public int getId() {
        return id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAcademyHour() {
        return academyHour;
    }

    public void setAcademyHour(String academyHour) {
        this.academyHour = academyHour;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerRawName() {
        return ownerRawName;
    }

    public void setOwnerRawName(String ownerRawName) {
        this.ownerRawName = ownerRawName;
    }

    public String getOwnerUserType() {
        return ownerUserType;
    }

    public void setOwnerUserType(String ownerUserType) {
        this.ownerUserType = ownerUserType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerFingerPrint() {
        return ownerFingerPrint;
    }

    public void setOwnerFingerPrint(String ownerFingerPrint) {
        this.ownerFingerPrint = ownerFingerPrint;
    }

    public String getOwnerEmployeeNumber() {
        return ownerEmployeeNumber;
    }

    public void setOwnerEmployeeNumber(String ownerEmployeeNumber) {
        this.ownerEmployeeNumber = ownerEmployeeNumber;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentRawName() {
        return assignmentRawName;
    }

    public void setAssignmentRawName(String assignmentRawName) {
        this.assignmentRawName = assignmentRawName;
    }

    public String getAssignmentCode() {
        return assignmentCode;
    }

    public void setAssignmentCode(String assignmentCode) {
        this.assignmentCode = assignmentCode;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentPlan() {
        return assignmentPlan;
    }

    public void setAssignmentPlan(String assignmentPlan) {
        this.assignmentPlan = assignmentPlan;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", _id='" + _id + '\'' +
                ", period='" + period + '\'' +
                ", academyHour='" + academyHour + '\'' +
                ", group='" + group + '\'' +
                ", language='" + language + '\'' +
                ", day='" + day + '\'' +
                ", modality='" + modality + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", ownerRawName='" + ownerRawName + '\'' +
                ", ownerUserType='" + ownerUserType + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerLastName='" + ownerLastName + '\'' +
                ", ownerFingerPrint='" + ownerFingerPrint + '\'' +
                ", ownerEmployeeNumber='" + ownerEmployeeNumber + '\'' +
                ", assignmentId='" + assignmentId + '\'' +
                ", assignmentRawName='" + assignmentRawName + '\'' +
                ", assignmentCode='" + assignmentCode + '\'' +
                ", assignmentName='" + assignmentName + '\'' +
                ", assignmentPlan='" + assignmentPlan + '\'' +
                '}';
    }
}
