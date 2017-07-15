package com.fime.fsw.huella.huella.Data.Modelos;

/**
 * Created by ensardz on 14/07/2017.
 */

public class UploadCheckout {
    private String _id;
    private Checkout checkout;

    public UploadCheckout(String _id, Checkout checkout) {
        this._id = _id;
        this.checkout = checkout;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }
}
