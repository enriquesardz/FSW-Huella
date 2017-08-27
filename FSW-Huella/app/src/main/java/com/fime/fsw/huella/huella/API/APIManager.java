package com.fime.fsw.huella.huella.API;

import android.text.TextUtils;
import android.util.Log;

import com.fime.fsw.huella.huella.API.Endpoints.APIServices;
import com.fime.fsw.huella.huella.Data.Modelos.LoginUser;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;

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
                if (response.isSuccessful() && tokenResponse != null) {
                    listener.response(tokenResponse);
                } else {
                    //Unauthorized user
                    //TODO: Agregar excepcion.
                    Log.e(TAG, "Error: " + response.body().toString());
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
                    //Si expira el token.
                }
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                listener.failure();
            }
        });
    }

}
