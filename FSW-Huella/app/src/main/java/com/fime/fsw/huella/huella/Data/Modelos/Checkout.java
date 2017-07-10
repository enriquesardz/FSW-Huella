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
}
