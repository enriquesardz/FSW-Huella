package com.fime.fsw.huella.huella.API;

/**
 * Created by ensardz on 07/08/2017.
 */

public interface APICallbackListener<T> {
    public void response(T response);
    public void failure();
}
