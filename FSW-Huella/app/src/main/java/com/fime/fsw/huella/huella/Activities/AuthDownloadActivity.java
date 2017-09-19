package com.fime.fsw.huella.huella.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICallbackListener;
import com.fime.fsw.huella.huella.API.APIManager;
import com.fime.fsw.huella.huella.Activities.InicioSesion.PrefectoLoginActivity;
import com.fime.fsw.huella.huella.Activities.RutasLista.RutasListaActivity;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import java.util.List;

import io.realm.Realm;

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
    private TextView txtSaludo;

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        String user = getIntent().getStringExtra("user");
        String password = getIntent().getStringExtra("password");
        txtSaludo = (TextView) findViewById(R.id.txtSaludo);
        txtSaludo.setText("");

        loginRequest(user, password);
    }

    //TODO: Que valide con la tabla de prefectos

    public void loginRequest(final String user, String password) {

        APIManager.getInstance().loginRequest(user, password, new APICallbackListener<TokenResponse>() {
            @Override
            public void response(TokenResponse tokenResponse) {

                txtSaludo.setText(getResources().getString(R.string.auth_saludo, user));
                txtSaludo.setTypeface(null, Typeface.BOLD);
                saveUserToken(user, tokenResponse);

                Log.i(TAG, "Login successful: " + tokenResponse.toString());
                startRouteListActivity(false);

            }

            @Override
            public void failure() {
                Toast.makeText(mContext, "Usuario no autorizado", Toast.LENGTH_SHORT).show();
                returnToLoginActivity(user);
                Log.e(TAG, "Bad user");
            }
        });

    }

    public String saveUserToken(String userName, TokenResponse tokenResponse) {
        //Guarda la sesion del usuario; el usuario ahora esta logeado.
        String token = tokenResponse.getToken();
        String refreshToken = tokenResponse.getRenew();
        mSesionApp.crearSesionLogin(userName, token, refreshToken);
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
        intent.putExtra("yaDescargo", downloadSuccessful);
        startActivity(intent);
        finish();
    }

}
