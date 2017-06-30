package com.fime.fsw.huella.huella.Objetos;

/**
 * Created by Quique on 11/06/2017.
 */

public class RecorridoActualItem {
    private long id;
    private String horaFime, salonFime;

    public RecorridoActualItem(long id, String hora, String salon){
        this.id = id;
        this.horaFime = hora;
        this.salonFime = salon;
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
}
