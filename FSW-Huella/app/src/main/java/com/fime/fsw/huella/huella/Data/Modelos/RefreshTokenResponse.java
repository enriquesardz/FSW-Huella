package com.fime.fsw.huella.huella.Data.Modelos;

/**
 * Created by quiqu on 27/08/2017.
 */

public class RefreshTokenResponse {
    //Response object for RefreshToken
    private String status;
    private String token;

    public RefreshTokenResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }
}
