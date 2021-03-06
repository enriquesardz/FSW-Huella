package com.example.ensardz.registrohuella.Datos;

/**
 * Created by ensardz on 18/08/2017.
 */

public class TokenResponse {
    private String status;
    private String token;
    private String refreshToken;

    public TokenResponse(String status, String token, String refreshToken) {
        this.status = status;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "status='" + status + '\'' +
                ", token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
