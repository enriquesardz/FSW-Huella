package com.fime.fsw.huella.huella.Data.Modelos;

/**
 * Created by Quique on 22/07/2017.
 */

public class TokenResponse {
    private String status;
    private String token;

    public TokenResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "status='" + status + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
