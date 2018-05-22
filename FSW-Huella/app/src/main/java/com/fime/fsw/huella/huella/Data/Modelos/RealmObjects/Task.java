package com.fime.fsw.huella.huella.Data.Modelos.RealmObjects;

import android.util.Log;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quique on 29/06/2017.
 */

public class Task extends RealmObject {

    //Table fields de Task
    public static final String _ID_FIELD = "_id";
    public static final String SEQUENCE_FIELD = "sequence";
    public static final String TASK_STATE_FIELD = "taskState";
    public static final String ROUTE_ID_FIELD = "routeId";
    public static final String IS_UPLOADED_FIELD = "isUploaded";


    private static final String HUELLA_ENRIQUE = "03015B1C0000C002C002C0008000800080008000800080008000800000008000800080008002C00200000000000000000000000000000000239013BE3614137E561B16DE2520915E3DA4909E0C2E263E23304DBE153C0B1E591F441F38AA4E3F152F0E5F48B249DF53B348BF3DB44B5F5A39087F5F3F1EFF19BFC91F4587561C73B9A07C678557DD6708C1BD4109803D7235DED556BCDF9D4A2C0C3A4FAD093A2FC0DEFB353FE0F70000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003015D1C00008002800000000000000000000000000000000000000000000000000080008000C002000000000000000000000000000000002310149E379413DE561B16DE2520917E3DA4909E23304DDE73C39F9E591F441F38AA4E3F152ECE9F48B249DF53B3489F3DB44B5F7235DEDF5A38C89F723A9FF74607561C4A2C0C3C4FAD88FC153C4B1C680557DD6688821D4109805D58BEDF3D19BF895D5FBF9EFD353FE0FB2FC0DEDB00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
    private static final String HUELLA_LEO = "030157120000FFFEFEFEF01EE00EE006C002C002C002C002C002800080008000800080008002C00200000000000000000000000000000000239BD49E35A615BE11B4901E70B6DB5E62B95A7E1BBECD7E6FC299DE33AAD47F3AB7935F463943DC2D3F4D3A2EC225FA5243599A4FBE6C5254BE18334EBC195740C3292C44BD9433000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003015E1A0000C0068002800200000000000000000000000000000000000000000000000000008002000000000000000000000000000000006008197E1F0DD59E0D9012963626159E32A9D43E11B4901E7036DB9E62391A7E1C3E8DBE3C422C1E259D549F4A42D47F6DC25A3C2DC2E4143BB7533D54B7571D2FBFCE751B94945217156AD2443C947A4CB8017B4139C1DB4F3BD8BB4A3E439B55419A936642D9B6000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
    private static final String HUELLA_JORGE = "03015E110000F03EE01EC00E8006800200020002000200020002000200020006000600068006800E000000000000000000000000000000003705187E5C0B9BBE3A96825E3D1B993E6625D9FE13B1903E18BB0D3E53BC47FE5241491E4D0C1A5F4518193F5C2383DF51A65A3F1E2A013F152A54BF613B073F293E075F00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
    public int currentSelected = 0;

    //Keys para el objeto Task
    public static final String PLAN_ID_KEY = "taskPlanId";
    public static final String MATERIA_KEY = "taskMateria";
    public static final String SALON_ID_KEY = "taskSalonId";
    public static final String AREA_ID_KEY = "taskAreaId";
    public static final String EDIFICIO_ID_KEY = "taskEdificioId";
    public static final String NUMERO_EMPLEADO_KEY = "taskNumeroEmpleado";
    public static final String NOMBRE_EMPLEADO_KEY = "taskNombreEmpleado";
    public static final String HUELLA_EMPLEADO_KEY = "taskHuellaEmpleado";
    public static final String TIPO_KEY = "taskTipo";

    //Estados del Task
    public static final int STATE_NO_HA_PASADO = 0;
    public static final int STATE_PASO_VINO_MAESTRO = 1;
    public static final int STATE_PASO_NO_VINO_MAESTRO = 2;

    @PrimaryKey
    private String _id;
    private String planId;
    private String materiaId;
    private String materia;
    private String salonId;
    private String areaId;
    private String edificioId;
    private String numeroEmpleado;
    private String nombreEmpleado;
    private String huellaEmpleado;
    private String tipo;
    private boolean isNexus;
    private int sequence;
    private int taskState;
    private Checkout checkout;
    private String routeId;
    private boolean isUploaded;
    private String prefectoId;
    private String horaId;

    public Task() { //TODO: Agregar los campos horaId
    }

    public static Task create(String _id, String routeId, String planId, String materiaId,
                              String materia, String salonId, String areaId, String edificioId,
                              String numeroEmpleado, String nombreEmpleado, String tipo,
                              boolean isNexus, int sequence, String prefectoId, String horaId) {
        Task task = new Task();
        task._id = _id;
        task.routeId = routeId;
        task.planId = planId;
        task.materiaId = materiaId;
        task.materia = materia;
        task.salonId = salonId;
        task.areaId = areaId;
        task.edificioId = edificioId;
        task.numeroEmpleado = numeroEmpleado;
        task.nombreEmpleado = nombreEmpleado;
        task.huellaEmpleado = getRandomHuella().trim(); // HUELLA_ENRIQUE;
        // TODO: REMOVE TEMPORALMENTE LEO PROJ
        task.nombreEmpleado = getEmployeeNameAltered(nombreEmpleado, task.huellaEmpleado);
        task.tipo = tipo;
        task.isNexus = isNexus;
        task.sequence = sequence;
        task.taskState = STATE_NO_HA_PASADO;
        task.checkout = new Checkout();
        task.isUploaded = false;
        task.prefectoId = prefectoId;
        task.horaId = horaId;

        return task;
    }

    private static String getRandomHuella() {
        int range = (2 - 1) + 1;
        int randomNumber = (int) (Math.random() * range) + 1;
        switch (randomNumber) {
            case 1:
                return HUELLA_LEO;
            case 2:
                return HUELLA_JORGE;
            default:
                return HUELLA_LEO;
        }
    }

    private static String getEmployeeNameAltered(String employeeName, String employeeFingerprint) {
        switch (employeeFingerprint) {
            case HUELLA_LEO:
                return employeeName + "Leo";
            case HUELLA_JORGE:
                return employeeName + "Jorge";
            default:
                return employeeName + "Leo";
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
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

    public boolean isNexus() {
        return isNexus;
    }

    public void setNexus(boolean nexus) {
        isNexus = nexus;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getHuellaEmpleado() {
        return huellaEmpleado;
    }

    public void setHuellaEmpleado(String huellaEmpleado) {
        this.huellaEmpleado = huellaEmpleado;
    }

    public String getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(String materiaId) {
        this.materiaId = materiaId;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getPrefectoId() {
        return prefectoId;
    }

    public void setPrefectoId(String prefectoId) {
        this.prefectoId = prefectoId;
    }

    public String getHoraId() {
        return horaId;
    }

    public void setHoraId(String horaId) {
        this.horaId = horaId;
    }
}
