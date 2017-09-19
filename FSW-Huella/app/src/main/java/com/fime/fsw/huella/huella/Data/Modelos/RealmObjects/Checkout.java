package com.fime.fsw.huella.huella.Data.Modelos.RealmObjects;

import io.realm.RealmObject;

/**
 * Created by Quique on 08/07/2017.
 */

public class Checkout extends RealmObject{

    //Keys para Checkout
    public static final String STARTED_AT_KEY = "startedAt";
    public static final String VISIT_AT_KEY = "visitAt";
    public static final String SIGNED_AT_KEY = "signedAt";
    public static final String FINISHED_AT_KEY = "finishedAt";

    private String startedAt;
    private String visitAt;
    private String signedAt;
    private String finishedAt;


    public Checkout (){
        this.startedAt = "";
        this.visitAt = "";
        this.signedAt = "";
        this.finishedAt = "";
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getVisitAt() {
        return visitAt;
    }

    public void setVisitAt(String visitAt) {
        this.visitAt = visitAt;
    }

    public String getSignedAt() {
        return signedAt;
    }

    public void setSignedAt(String signedAt) {
        this.signedAt = signedAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "startedAt='" + startedAt + '\'' +
                ", visitAt='" + visitAt + '\'' +
                ", signedAt='" + signedAt + '\'' +
                ", finishedAt='" + finishedAt + '\'' +
                '}';
    }
}
