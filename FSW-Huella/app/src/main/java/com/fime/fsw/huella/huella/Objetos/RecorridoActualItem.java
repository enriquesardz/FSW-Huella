package com.fime.fsw.huella.huella.Objetos;

/**
 * Created by Quique on 11/06/2017.
 */

public class RecorridoActualItem {
    private long id;
    private String horaFime, salonFime;
    private int recorridoState;

    public RecorridoActualItem(long id, String hora, String salon, int state){
        this.id = id;
        this.horaFime = hora;
        this.salonFime = salon;
        this.recorridoState = state;
    }

    public String getHoraFime(){
        return this.horaFime;
    }

    public String getSalonFime(){
        return this.salonFime;
    }

    public long getID(){
        return this.id;
    }

    public int getRecorridoState() {
        return recorridoState;
    }
}
