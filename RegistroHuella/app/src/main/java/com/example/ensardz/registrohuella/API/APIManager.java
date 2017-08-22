package com.example.ensardz.registrohuella.API;

import com.example.ensardz.registrohuella.Datos.LoginUser;
import com.example.ensardz.registrohuella.Datos.Professor;
import com.example.ensardz.registrohuella.Datos.TokenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ensardz on 18/08/2017.
 */

public class APIManager {

    public static APIManager getInstance() {
        return new APIManager();
    }

    public void loginRequest(String user, String password, final APICallbackListener<TokenResponse> listener) {
        APIServices service = APICodo.requestToken().create(APIServices.class);
        LoginUser loginUser = new LoginUser(user, password);
        Call<TokenResponse> call = service.authGetToken(loginUser);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                TokenResponse tokenResponse = response.body();
                if (response.isSuccessful() && tokenResponse != null){
                    listener.response(tokenResponse);
                } else{
                    //Unauthorized user or some other stuff
                    listener.response(null);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                listener.failure();
            }
        });
    }

    public void downloadProfessors(final APICallbackListener<List<Professor>> listener) {

        loginRequest("Enrique", "123", new APICallbackListener<TokenResponse>() {
            @Override
            public void response(TokenResponse tokenResponse) {

                if(tokenResponse == null){
                    return;
                }

                String jwtToken = tokenResponse.getToken();

                if (jwtToken == null){
                    return;
                }

                APIServices service = APICodo.signedAllProfessors().create(APIServices.class);
                Call<List<Professor>> call = service.downloadProfessors(jwtToken);

                call.enqueue(new Callback<List<Professor>>() {
                    @Override
                    public void onResponse(Call<List<Professor>> call, Response<List<Professor>> response) {
                        if (response.isSuccessful() && response.body() != null){
                            if (!response.body().isEmpty()){
                                listener.response(response.body());
                            }
                        } else{
                            //Unsuccessful download or unauthorized user
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Professor>> call, Throwable t) {
                        //Some random error
                    }
                });

            }

            @Override
            public void failure() {
                //Couldn't connect to auth
            }
        });
    }

    public void uploadProfessor(final APICallbackListener<List<Professor>> listener){

    }
}
