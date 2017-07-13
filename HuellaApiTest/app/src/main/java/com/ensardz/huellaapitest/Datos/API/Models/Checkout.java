package com.ensardz.huellaapitest.Datos.API.Models;

import io.realm.RealmObject;

/**
 * Created by ensardz on 12/07/2017.
 */

public class Checkout extends RealmObject {

    private String statusCode;
    private String createdAt;
    private String startedAt;
    private String visitAt;
    private String signedAt;
    private String finishedAt;
    private String updatedAt;

    public static Checkout create(String statusCode, String createdAt, String startedAt, String visitAt, String signedAt, String finishedAt, String updatedAt) {
        Checkout checkout = new Checkout();
        checkout.statusCode = statusCode;
        checkout.createdAt = createdAt;
        checkout.startedAt = startedAt;
        checkout.visitAt = visitAt;
        checkout.signedAt = signedAt;
        checkout.finishedAt = finishedAt;
        checkout.updatedAt = updatedAt;
        return checkout;
    }
}
