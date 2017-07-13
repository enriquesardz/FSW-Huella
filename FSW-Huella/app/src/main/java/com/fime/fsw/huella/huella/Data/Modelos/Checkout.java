package com.fime.fsw.huella.huella.Data.Modelos;

import io.realm.RealmObject;

/**
 * Created by Quique on 08/07/2017.
 */

public class Checkout extends RealmObject{

    private String statusCode;
    private String createdAt;
    private String startedAt;
    private String visitAt;
    private String signedAt;
    private String finishedAt;
    private String updatedAt;

    public Checkout (){}

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "statusCode='" + statusCode + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", startedAt='" + startedAt + '\'' +
                ", visitAt='" + visitAt + '\'' +
                ", signedAt='" + signedAt + '\'' +
                ", finishedAt='" + finishedAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
