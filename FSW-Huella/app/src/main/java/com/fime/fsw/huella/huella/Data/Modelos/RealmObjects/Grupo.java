package com.fime.fsw.huella.huella.Data.Modelos.RealmObjects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ensardz on 08/09/2017.
 */

public class Grupo extends RealmObject {

    @PrimaryKey
    private int id;
    private String planId;
    private String materiaId;
    private String materia;
    private String horarioId;
    private String grupo;
    private String salonId;
    private String areaId;
    private String edificioId;
    private int secuenciaUno;
    private int secuenciaDos;
    private int secuenciaTres;
    private String dia;
    private String numeroEmpleado;
    private String nombreEmpleado;
    private String tipo;
    private String periodoId;
    private String tipoPeriodoId;
    private String nivelAcademicoId;
    private String gradoAcademicoId;
    private boolean nexus;
    private String calendarioId;
    private String inscripcionId;

    public static Grupo create(int id, String planId, String materiaId, String materia, String horarioId, String grupo, String salonId, String areaId, String edificioId, int secuenciaUno, int secuenciaDos, int secuenciaTres, String dia, String numeroEmpleado, String nombreEmpleado, String tipo, String periodoId, String tipoPeriodoId, String nivelAcademicoId, String gradoAcademicoId, boolean nexus, String calendarioId, String inscripcionId) {
        Grupo grupoObj = new Grupo();
        grupoObj.id = id;
        grupoObj.planId = planId;
        grupoObj.materiaId = materiaId;
        grupoObj.materia = materia;
        grupoObj.horarioId = horarioId;
        grupoObj.grupo = grupo;
        grupoObj.salonId = salonId;
        grupoObj.areaId = areaId;
        grupoObj.edificioId = edificioId;
        grupoObj.secuenciaUno = secuenciaUno;
        grupoObj.secuenciaDos = secuenciaDos;
        grupoObj.secuenciaTres = secuenciaTres;
        grupoObj.dia = dia;
        grupoObj.numeroEmpleado = numeroEmpleado;
        grupoObj.nombreEmpleado = nombreEmpleado;
        grupoObj.tipo = tipo;
        grupoObj.periodoId = periodoId;
        grupoObj.tipoPeriodoId = tipoPeriodoId;
        grupoObj.nivelAcademicoId = nivelAcademicoId;
        grupoObj.gradoAcademicoId = gradoAcademicoId;
        grupoObj.nexus = nexus;
        grupoObj.calendarioId = calendarioId;
        grupoObj.inscripcionId = inscripcionId;
        return grupoObj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(String materiaId) {
        this.materiaId = materiaId;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(String horarioId) {
        this.horarioId = horarioId;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getSalonId() {
        return salonId;
    }

    public void setSalonId(String salonId) {
        this.salonId = salonId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getEdificioId() {
        return edificioId;
    }

    public void setEdificioId(String edificioId) {
        this.edificioId = edificioId;
    }

    public int getSecuenciaUno() {
        return secuenciaUno;
    }

    public void setSecuenciaUno(int secuenciaUno) {
        this.secuenciaUno = secuenciaUno;
    }

    public int getSecuenciaDos() {
        return secuenciaDos;
    }

    public void setSecuenciaDos(int secuenciaDos) {
        this.secuenciaDos = secuenciaDos;
    }

    public int getSecuenciaTres() {
        return secuenciaTres;
    }

    public void setSecuenciaTres(int secuenciaTres) {
        this.secuenciaTres = secuenciaTres;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(String periodoId) {
        this.periodoId = periodoId;
    }

    public String getTipoPeriodoId() {
        return tipoPeriodoId;
    }

    public void setTipoPeriodoId(String tipoPeriodoId) {
        this.tipoPeriodoId = tipoPeriodoId;
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

    public boolean isNexus() {
        return nexus;
    }

    public void setNexus(boolean nexus) {
        this.nexus = nexus;
    }

    public String getCalendarioId() {
        return calendarioId;
    }

    public void setCalendarioId(String calendarioId) {
        this.calendarioId = calendarioId;
    }

    public String getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(String inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "id=" + id +
                ", planId='" + planId + '\'' +
                ", materiaId='" + materiaId + '\'' +
                ", materia='" + materia + '\'' +
                ", horarioId='" + horarioId + '\'' +
                ", grupo='" + grupo + '\'' +
                ", salonId='" + salonId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", edificioId='" + edificioId + '\'' +
                ", secuenciaUno=" + secuenciaUno +
                ", secuenciaDos=" + secuenciaDos +
                ", secuenciaTres=" + secuenciaTres +
                ", dia='" + dia + '\'' +
                ", numeroEmpleado='" + numeroEmpleado + '\'' +
                ", nombreEmpleado='" + nombreEmpleado + '\'' +
                ", tipo='" + tipo + '\'' +
                ", periodoId='" + periodoId + '\'' +
                ", tipoPeriodoId='" + tipoPeriodoId + '\'' +
                ", nivelAcademicoId='" + nivelAcademicoId + '\'' +
                ", gradoAcademicoId='" + gradoAcademicoId + '\'' +
                ", nexus=" + nexus +
                ", calendarioId='" + calendarioId + '\'' +
                ", inscripcionId='" + inscripcionId + '\'' +
                '}';
    }
}
