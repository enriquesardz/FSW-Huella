package com.ensardz.huellaapitest.Datos.Objetos;

/**
 * Created by Quique on 28/06/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HuellaResponse {

    @SerializedName("m")
    @Expose
    private List<M> m = null;
    @SerializedName("v")
    @Expose
    private List<Object> v = null;
    @SerializedName("n")
    @Expose
    private List<Object> n = null;

    public List<M> getM() {
        return m;
    }

    public void setM(List<M> m) {
        this.m = m;
    }

    public List<Object> getV() {
        return v;
    }

    public void setV(List<Object> v) {
        this.v = v;
    }

    public List<Object> getN() {
        return n;
    }

    public void setN(List<Object> n) {
        this.n = n;
    }

}




