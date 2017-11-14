package com.fime.fsw.huella.huella.Data.Modelos.RealmObjects;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ensardz on 08/09/2017.
 */

public class Prefecto extends RealmObject implements Parcelable {

    public static final String USUARIO_FIELD = "usuario";
    public static final String PASSWORD_FIELD = "password";

    @PrimaryKey
    private String prefectoId;
    private String nombre;
    private String usuario;
    private String password;
    private String huella;
    private String areaId;
    private String tipoPeriodoId;
    private String periodoId;
    private String tipo;
    private String nivelAcademicoId;
    private String gradoAcademicoId;
    private String inscripcionId;

    public static Prefecto create(String prefectoId, String nombre, String usuario, String password, String huella, String areaId, String tipoPeriodoId, String periodoId, String tipo, String nivelAcademicoId, String gradoAcademicoId, String inscripcionId) {
        Prefecto prefecto = new Prefecto();
        prefecto.prefectoId = prefectoId;
        prefecto.nombre = nombre;
        prefecto.usuario = usuario;
        prefecto.password = password;
        prefecto.huella = huella;
        prefecto.areaId = areaId;
        prefecto.tipoPeriodoId = tipoPeriodoId;
        prefecto.periodoId = periodoId;
        prefecto.tipo = tipo;
        prefecto.nivelAcademicoId = nivelAcademicoId;
        prefecto.gradoAcademicoId = gradoAcademicoId;
        prefecto.inscripcionId = inscripcionId;
        return prefecto;
    }

    public String getPrefectoId() {
        return prefectoId;
    }

    public void setPrefectoId(String prefectoId) {
        this.prefectoId = prefectoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHuella() {
        return huella;
    }

    public void setHuella(String huella) {
        this.huella = huella;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getTipoPeriodoId() {
        return tipoPeriodoId;
    }

    public void setTipoPeriodoId(String tipoPeriodoId) {
        this.tipoPeriodoId = tipoPeriodoId;
    }

    public String getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(String periodoId) {
        this.periodoId = periodoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNivelAcademicoId() {
        return nivelAcademicoId;
    }

    public void setNivelAcademicoId(String nivelAcademicoId) {
        this.nivelAcademicoId = nivelAcademicoId;
    }

    public String getGradoAcademicoId() {
        return gradoAcademicoId;
    }

    public void setGradoAcademicoId(String gradoAcademicoId) {
        this.gradoAcademicoId = gradoAcademicoId;
    }

    public String getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(String inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    @Override
    public String toString() {
        return "Prefecto{" +
                "prefectoId='" + prefectoId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                ", huella='" + huella + '\'' +
                ", areaId='" + areaId + '\'' +
                ", tipoPeriodoId='" + tipoPeriodoId + '\'' +
                ", periodoId='" + periodoId + '\'' +
                ", tipo='" + tipo + '\'' +
                ", nivelAcademicoId='" + nivelAcademicoId + '\'' +
                ", gradoAcademicoId='" + gradoAcademicoId + '\'' +
                ", inscripcionId='" + inscripcionId + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.prefectoId);
        dest.writeString(this.nombre);
        dest.writeString(this.usuario);
        dest.writeString(this.password);
        dest.writeString(this.huella);
        dest.writeString(this.areaId);
        dest.writeString(this.tipoPeriodoId);
        dest.writeString(this.periodoId);
        dest.writeString(this.tipo);
        dest.writeString(this.nivelAcademicoId);
        dest.writeString(this.gradoAcademicoId);
        dest.writeString(this.inscripcionId);
    }

    public Prefecto() {
    }

    protected Prefecto(Parcel in) {
        this.prefectoId = in.readString();
        this.nombre = in.readString();
        this.usuario = in.readString();
        this.password = in.readString();
        this.huella = in.readString();
        this.areaId = in.readString();
        this.tipoPeriodoId = in.readString();
        this.periodoId = in.readString();
        this.tipo = in.readString();
        this.nivelAcademicoId = in.readString();
        this.gradoAcademicoId = in.readString();
        this.inscripcionId = in.readString();
    }

    public static final Parcelable.Creator<Prefecto> CREATOR = new Parcelable.Creator<Prefecto>() {
        @Override
        public Prefecto createFromParcel(Parcel source) {
            return new Prefecto(source);
        }

        @Override
        public Prefecto[] newArray(int size) {
            return new Prefecto[size];
        }
    };
}
