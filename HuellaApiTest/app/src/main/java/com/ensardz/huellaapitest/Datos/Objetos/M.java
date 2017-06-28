package com.ensardz.huellaapitest.Datos.Objetos;

/**
 * Created by Quique on 28/06/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

    @SerializedName("plan")
    @Expose
    private Integer plan;
    @SerializedName("roomType")
    @Expose
    private Integer roomType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("roomDescription")
    @Expose
    private String roomDescription;
    @SerializedName("assigmentCode")
    @Expose
    private String assigmentCode;
    @SerializedName("assigment")
    @Expose
    private String assigment;
    @SerializedName("academyHour")
    @Expose
    private String academyHour;
    @SerializedName("startClassAt")
    @Expose
    private String startClassAt;
    @SerializedName("finishClassAt")
    @Expose
    private String finishClassAt;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("checkout")
    @Expose
    private Checkout checkout;

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
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

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getAssigmentCode() {
        return assigmentCode;
    }

    public void setAssigmentCode(String assigmentCode) {
        this.assigmentCode = assigmentCode;
    }

    public String getAssigment() {
        return assigment;
    }

    public void setAssigment(String assigment) {
        this.assigment = assigment;
    }

    public String getAcademyHour() {
        return academyHour;
    }

    public void setAcademyHour(String academyHour) {
        this.academyHour = academyHour;
    }

    public String getStartClassAt() {
        return startClassAt;
    }

    public void setStartClassAt(String startClassAt) {
        this.startClassAt = startClassAt;
    }

    public String getFinishClassAt() {
        return finishClassAt;
    }

    public void setFinishClassAt(String finishClassAt) {
        this.finishClassAt = finishClassAt;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }

}