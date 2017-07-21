package com.fime.fsw.huella.huella.Data.Modelos;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Quique on 19/07/2017.
 */

public class Room extends RealmObject {
    //Keys para Room
    public static String BUILDING_KEY = "building";
    public static String BARCODE_KEY = "barcode";
    public static String ROOM_NUMBER_KEY = "roomNumber";
    public static String AREA_KEY = "area";

    private String building;
    private String barcode;
    @SerializedName("room")
    private String roomNumber;
    private String area;

    public static Room create(String building, String barcode, String roomNumber, String area) {
        Room room = new Room();
        room.building = building;
        room.barcode = barcode;
        room.roomNumber = roomNumber;
        room.area = area;
        return room;
    }

    public String getBuilding() {
        return building;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "Room{" +
                "building='" + building + '\'' +
                ", barcode='" + barcode + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
