package com.fime.fsw.huella.huella.Data.Modelos;

import io.realm.RealmObject;

/**
 * Created by Quique on 19/07/2017.
 */

public class Assignment extends RealmObject {

    //Keys para Assignment
    public static final String RAW_NAME_KEY = "rawName";
    public static final String CODE_KEY = "code";
    public static final String NAME_KEY = "name";
    public static final String PLAN_KEY = "plan";

    private String rawName;
    private String code;
    private String name;
    private String plan;

    public static Assignment create(String rawName, String code, String name, String plan) {
        Assignment assignment = new Assignment();
        assignment.rawName = rawName;
        assignment.code = code;
        assignment.name = name;
        assignment.plan = plan;
        return assignment;
    }

    public String getRawName() {
        return rawName;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPlan() {
        return plan;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "rawName='" + rawName + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", plan='" + plan + '\'' +
                '}';
    }
}
