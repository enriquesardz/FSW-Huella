package com.fime.fsw.huella.huella.Data.Modelos;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Checkout;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ensardz on 01/08/2017.
 */

public class UploadCheckouts {

    private Data data;

    public UploadCheckouts(Data data) {
        this.data = data;
    }

    public static UploadCheckouts create(List<Task> tasks){
        List<UpCheckout> upCheckouts = new ArrayList<UpCheckout>();

        for (Task task : tasks){
            upCheckouts.add(new UpCheckout(
                    task.getPrefectoId(),
                    task.getNumeroEmpleado(),
                    task.getMateriaId(),
                    task.getHoraId(),
                    task.getSalonId(),
                    task.getCheckout()));
        }
        Data data = new Data(upCheckouts);
        return new UploadCheckouts(data);
    }
}


class UpCheckout {
    //Task checkout
    private String statusCode;
    private String prefectoId;
    private String maestroId;
    private String materiaId;
    private String horaId;
    private String salonId;
    private String id;
    private String date;
    private String startedAt;
    private String visitAt;
    private String signedAt;
    private String finishedAt;

    //TODO: Quitar los datos hardcodeados

    public UpCheckout(String prefectoId, String maestroId, String materiaId, String horaId, String salonId, Checkout checkout) {

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = format.format(today);

        this.statusCode = "32";
        this.prefectoId = prefectoId;
        this.maestroId = maestroId;
        this.materiaId = materiaId;
        this.horaId = horaId;
        this.salonId = salonId;
        this.id = prefectoId + maestroId + materiaId + horaId + salonId;
        this.date = todayDate;
        this.startedAt = checkout.getStartedAt();
        this.visitAt = checkout.getVisitAt();
        this.signedAt = checkout.getSignedAt();
        this.finishedAt = checkout.getFinishedAt();
    }

}

class Data {
    //Route id
    private List<UpCheckout> checkouts;

    public Data(List<UpCheckout> checkouts) {
        this.checkouts = checkouts;
    }
}
