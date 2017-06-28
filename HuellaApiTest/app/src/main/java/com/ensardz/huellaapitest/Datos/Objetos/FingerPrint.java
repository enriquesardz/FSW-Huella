package com.ensardz.huellaapitest.Datos.Objetos;

/**
 * Created by Quique on 28/06/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FingerPrint {

    @SerializedName("hexCode")
    @Expose
    private String hexCode;

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

}