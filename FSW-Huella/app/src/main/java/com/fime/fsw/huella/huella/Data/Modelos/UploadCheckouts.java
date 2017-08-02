package com.fime.fsw.huella.huella.Data.Modelos;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by ensardz on 01/08/2017.
 */

public class UploadCheckouts {

    private Data data;

//    public static UploadCheckouts getRealmCheckoutsAsJson(RealmResults<Task> tasks){
//        List<UpCheckout> chckouts = new ArrayList<UpCheckout>();
//        for (Task task : tasks){
//            chckouts.add(new UpCheckout(String.valueOf(task.get_id()), mRealm.copyFromRealm(task.getCheckout())));
//        }
//    }
//
//    public static String getCheckoutsFromRealmToJson() {
//        String routeId = "";
//        List<UpCheckout> uploadCheckouts = new ArrayList<UpCheckout>();
//        Data data = new Data(routeId,uploadCheckouts);
//        Gson gson = new Gson();
//        String json = gson.toJson(data);
//        return json;
//    }

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
