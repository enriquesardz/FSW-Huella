package com.fime.fsw.huella.huella.Data.Modelos;

/**
 * Created by ensardz on 03/08/2017.
 */

public class UploadResponse {
    private String status,messages;

    public UploadResponse(String status, String messages) {
        this.status = status;
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public String getMessages() {
        return messages;
    }
}
