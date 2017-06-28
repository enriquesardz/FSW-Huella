package com.ensardz.huellaapitest.Datos.Objetos;

/**
 * Created by Quique on 28/06/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checkout {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("startedAt")
    @Expose
    private String startedAt;
    @SerializedName("visitAt")
    @Expose
    private String visitAt;
    @SerializedName("signedAt")
    @Expose
    private String signedAt;
    @SerializedName("finishedAt")
    @Expose
    private String finishedAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
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

}