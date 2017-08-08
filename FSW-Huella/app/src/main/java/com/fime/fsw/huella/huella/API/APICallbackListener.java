package com.fime.fsw.huella.huella.API;

import com.fime.fsw.huella.huella.Data.Modelos.Task;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ensardz on 07/08/2017.
 */

public interface APICallbackListener<T> {
    public void response(T response);
    public void failure();
}
