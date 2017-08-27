package com.fime.fsw.huella.huella.Data.Modelos;

/**
 * Created by Quique on 22/07/2017.
 */

public class TokenResponse {

    private String status;
    private String token;
    private String renew;

    public TokenResponse(String status, String token, String renew) {
        this.status = status;
        this.token = token;
        this.renew = renew;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getRenew() {
        return renew;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "status='" + status + '\'' +
                ", token='" + token + '\'' +
                ", renew='" + renew + '\'' +
                '}';
    }
}
