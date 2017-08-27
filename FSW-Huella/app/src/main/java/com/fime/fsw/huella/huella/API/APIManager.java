package com.fime.fsw.huella.huella.API;

import android.text.TextUtils;
import android.util.Log;

import com.fime.fsw.huella.huella.API.Endpoints.APIServices;
import com.fime.fsw.huella.huella.Data.Modelos.APICodoResponse;
import com.fime.fsw.huella.huella.Data.Modelos.LoginUser;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.RefreshTokenResponse;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;
import com.fime.fsw.huella.huella.Data.Modelos.UploadRefreshToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

/**
 * Created by ensardz on 07/08/2017.
 */

public class APIManager {

    private static final String TAG = APP_TAG + APIManager.class.getSimpleName();
    private static final String STATUS_SUCESS = "success";


    public static APIManager getInstance() {
        return new APIManager();
    }

    public void loginRequest(final String user, String password, final APICallbackListener<TokenResponse> listener) {

        /*
        * Tries to get a token that grants access to the rest of the API, it returns
        * to the login Activity onFailure
        * Proceeds to the download of Routes and Tasks onSuccess.
        * */

        final LoginUser loginUser = new LoginUser(user, password);

        APIServices service = APICodo.requestToken().create(APIServices.class);
        Call<TokenResponse> call = service.authGetToken(loginUser);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                TokenResponse tokenResponse = response.body();

                if (response.isSuccessful() && TextUtils.equals(tokenResponse.getStatus().toLowerCase(), STATUS_SUCESS) && tokenResponse != null) {
                    listener.response(tokenResponse);
                } else {
                    //Unauthorized user
                    //TODO: Agregar excepcion.
                    Log.e(TAG, "Error: " + response.body().toString());
                    listener.failure();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                listener.failure();
            }
        });
    }

    public void startRouteAndTasksDownload(final String jwtToken, final APICallbackListener<List<Route>> listener) {

        APIServices service = APICodo.signedAllRoutesAndTasks().create(APIServices.class);
        Call<List<Route>> call = service.descargaAllRoutesWTasks(jwtToken);

        call.enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.response(response.body());
                } else {
                    //TODO: Agregar excepcion
                }

                if (TextUtils.equals(response.message().toLowerCase(), "unauthorized")){
                    Log.d(TAG, response.message().toLowerCase());
                    //TODO: Si expira el token.
                    refreshToken(new APICallbackListener<RefreshTokenResponse>() {
                        @Override
                        public void response(RefreshTokenResponse response) {
                            
                        }

                        @Override
                        public void failure() {

                        }
                    });
                    listener.failure();
                }
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                listener.failure();
            }
        });
    }

    public void refreshToken(final APICallbackListener<RefreshTokenResponse> listener){

        String refreshToken = ""; //get refreshToken

        APIServices service = APICodo.refreshToken().create(APIServices.class);
        Call<RefreshTokenResponse> call = service.authRefreshToken(new UploadRefreshToken(refreshToken));

        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {

                RefreshTokenResponse rtResponse = response.body();

                if(response.isSuccessful() && response.body() != null){
                    if (TextUtils.equals(rtResponse.getStatus().toLowerCase(), STATUS_SUCESS)){
                        listener.response(rtResponse);
                    } else {
                        listener.failure();
                    }
                } else {
                    listener.failure();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                listener.failure();
            }
        });
    }



}
