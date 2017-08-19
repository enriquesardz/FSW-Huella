package com.example.ensardz.registrohuella.API;

/**
 * Created by ensardz on 18/08/2017.
 */

public interface APICallbackListener<T> {
    public void response(T response);
    public void failure();
}
