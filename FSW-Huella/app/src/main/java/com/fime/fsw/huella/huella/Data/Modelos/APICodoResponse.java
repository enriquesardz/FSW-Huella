package com.fime.fsw.huella.huella.Data.Modelos;

import java.util.List;

/**
 * Created by ensardz on 27/08/2017.
 */

public class APICodoResponse<T> {
    private String status;
    private T data;

    public APICodoResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }
}
