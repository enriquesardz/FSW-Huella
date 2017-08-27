package com.fime.fsw.huella.huella.Data.Modelos;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Checkout;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ensardz on 01/08/2017.
 */

public class UploadCheckouts {

    private Data data;

    public UploadCheckouts(Data data) {
        this.data = data;
    }

    public static UploadCheckouts create(String routeId, List<Task> tasks){
        List<UpCheckout> upCheckouts = new ArrayList<UpCheckout>();
        for (Task task : tasks){
            upCheckouts.add(new UpCheckout(task.get_id(),task.getCheckout()));
        }
        Data data = new Data(routeId,upCheckouts);
        return new UploadCheckouts(data);
    }
}


class UpCheckout {
    //Task id
    private String _id;
    //Task checkout
    private Checkout checkout;

    public UpCheckout(String _id, Checkout checkout) {
        this._id = _id;
        this.checkout = checkout;
    }

}

class Data {
    //Route id
    private String id;
    private List<UpCheckout> checkouts;

    public Data(String id, List<UpCheckout> checkouts) {
        this.id = id;
        this.checkouts = checkouts;
    }
}
