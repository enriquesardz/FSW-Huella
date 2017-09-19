package com.fime.fsw.huella.huella.API;

import android.text.TextUtils;
import android.util.Log;

import com.fime.fsw.huella.huella.API.Endpoints.APIServices;
import com.fime.fsw.huella.huella.Data.Modelos.LoginUser;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Grupo;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Prefecto;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;
import com.fime.fsw.huella.huella.Data.Modelos.UploadCheckouts;
import com.fime.fsw.huella.huella.Data.Modelos.UploadResponse;

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

    //TODO: Usar
    public void downloadGrupos(final String jwtToken, String date, final APICallbackListener<List<Grupo>> listener) {

        APIServices service = APICodo.getAllGroups().create(APIServices.class);
        Call<List<Grupo>> call = service.downloadGroups(date);

        call.enqueue(new Callback<List<Grupo>>() {
            @Override
            public void onResponse(Call<List<Grupo>> call, Response<List<Grupo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.response(response.body());
                } else {
                    //TODO: Agregar excepcion
                    listener.failure();
                }

                if (TextUtils.equals(response.message().toLowerCase(), "unauthorized")){
                    Log.d(TAG, response.message().toLowerCase());
                    //TODO: Si expira el token.
                    listener.failure();
                }
            }

            @Override
            public void onFailure(Call<List<Grupo>> call, Throwable t) {
                listener.failure();
            }
        });
    }

    public void downloadPrefectos(final APICallbackListener<List<Prefecto>> listener){
        APIServices service = APICodo.getPrefectos().create(APIServices.class);
        Call<List<Prefecto>> call = service.downloadPrefectos();

        call.enqueue(new Callback<List<Prefecto>>() {
            @Override
            public void onResponse(Call<List<Prefecto>> call, Response<List<Prefecto>> response) {
                if (response.isSuccessful() && response.body() != null){
                    listener.response(response.body());
                } else{
                    listener.failure();
                }
            }

            @Override
            public void onFailure(Call<List<Prefecto>> call, Throwable t) {
                listener.failure();
            }
        });
    }

    public void uploadCheckouts(UploadCheckouts uploadCheckouts, final APICallbackListener<UploadResponse> listener){
        APIServices service = APICodo.uploadCheckouts().create(APIServices.class);
        Call<UploadResponse> call = service.uploadCheckouts(uploadCheckouts);

        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if(response.isSuccessful() && TextUtils.equals(response.body().getStatus(), STATUS_SUCESS)){
                    //Si se subieron se supone
                    listener.response(response.body());
                } else {
                    listener.failure();
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                listener.failure();
            }
        });
    }

}
