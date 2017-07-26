package com.fime.fsw.huella.huella.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICodo;
import com.fime.fsw.huella.huella.API.Endpoints.APIServices;
import com.fime.fsw.huella.huella.Activities.InicioSesion.PrefectoLoginActivity;
import com.fime.fsw.huella.huella.Activities.RutasLista.RutasListaActivity;
import com.fime.fsw.huella.huella.Data.Modelos.LoginUser;
import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

public class AuthDownloadActivity extends AppCompatActivity {

    /* User tries to get access to the API using their credentials

    * onSuccess: Saves token that grants access to the rest of the API endpoints and
    * then starts the Route list and Task lists download
    *
    * onFailure: Returns to the login activity if the loginRequest fails.
    *
    * */

    private static final String TAG = APP_TAG + AuthDownloadActivity.class.getSimpleName();

    private Context mContext;
    private SesionAplicacion mSesionApp;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_download);

        mContext = AuthDownloadActivity.this;
        mRealm = Realm.getDefaultInstance();
        mSesionApp = new SesionAplicacion(mContext);

        initComponentes();
    }

    public void initComponentes() {
        String user = getIntent().getStringExtra("user");
        String password = getIntent().getStringExtra("password");

        loginRequest(user, password);
    }

    public void loginRequest(final String user, String password) {
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
                    String jwtToken = saveUserToken(user, tokenResponse);
                    Log.i(TAG, "Login successful: " + tokenResponse.toString());
                    startRouteAndTasksDownload(jwtToken);
                } else {
                    Toast.makeText(mContext, "Usuario no autorizado", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Unauthorized user");
                    returnToLoginActivity(user);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(mContext, "Error de conexion", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error con requestToken service");
                returnToLoginActivity(user);
            }
        });
    }

    public void startRouteAndTasksDownload(final String jwtToken) {

        APIServices service = APICodo.signedRouteList().create(APIServices.class);
        Call<List<Route>> call = service.descargaRutas(jwtToken);

        call.enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                List<Route> routes = response.body();
                int routeCount;
                boolean isSuccess = false;
                if (response.isSuccessful() && routes != null) {
                    routeCount = routes.size();
                    RealmProvider.saveRouteListToRealm(mRealm, routes);
                    for(Route route : routes){
                        String routeId = route.get_id();
                        //TODO: Muchos requests, ver si hay una alternativa
//                        isSuccess = downloadTasksByRouteId(routeId, jwtToken);
                    }
                } else {
                    startRouteListActivity(false);
                }

                //Guarda los datos al Realm
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                Toast.makeText(mContext, "Fallo en la descarga", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void downloadTasksByRouteId(String routeId, String jwtToken){

        //Se actualizan los Routes con sus valores faltantes

        APIServices service = APICodo.signedSingleRoute().create(APIServices.class);
        Call<Route> call = service.descargaRecorrido(routeId,jwtToken);
        OkHttpClient client = new OkHttpClient();
        client.dispatcher().cancelAll();
        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                //Se ejecuta si el webservice regresa algo, la respuesta
                //es una lista de Tasks, entonces la respuesta se guarda en una Lista de tipo Tasks

                Route routeResponse = response.body();

                if(response.isSuccessful() && routeResponse != null){
                    RealmProvider.saveRouteToRealm(mRealm, routeResponse);
                }
            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                //No se descargo nada
                Toast.makeText(mContext, "No se descargo.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String saveUserToken(String userName, TokenResponse tokenResponse) {
        //Guarda la sesion del usuario; el usuario ahora esta logeado.
        String token = "JWT " + tokenResponse.getToken();
        mSesionApp.crearSesionLogin(userName, token);
        return token;
    }

    public void returnToLoginActivity(String user) {
        Intent intent = new Intent(mContext, PrefectoLoginActivity.class);
        intent.putExtra("user", user);

        startActivity(intent);
        finish();
    }

    public void startRouteListActivity(boolean downloadSuccessful) {
        Intent intent = new Intent(mContext, RutasListaActivity.class);
        //TODO: Put extra
        startActivity(intent);
        finish();
    }

}
