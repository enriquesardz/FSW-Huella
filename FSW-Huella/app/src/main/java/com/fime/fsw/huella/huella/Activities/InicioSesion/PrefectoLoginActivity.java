package com.fime.fsw.huella.huella.Activities.InicioSesion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICodo;
import com.fime.fsw.huella.huella.API.ServiciosAPI.UserLoginAuthService;
import com.fime.fsw.huella.huella.Activities.RutasLista.RutasListaActivity;
import com.fime.fsw.huella.huella.Data.Modelos.LoginUser;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

public class PrefectoLoginActivity extends AppCompatActivity {

    private static final String TAG = APP_TAG + PrefectoLoginActivity.class.getSimpleName();

    private Button btnIniciarSesion;
    private EditText etUser,etPassword;

    private Context mContext;
    private SesionAplicacion mSesionApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefecto_login);

        mContext = this;
        mSesionApp = new SesionAplicacion(mContext);

        initComponentes();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initComponentes() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.prefecto_login_titulo));
        }

        btnIniciarSesion = (Button) findViewById(R.id.iniciar_sesion_button);
        etUser = (EditText)findViewById(R.id.user_edittext);
        etPassword = (EditText)findViewById(R.id.password_edittext);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginRequest();

            }
        });
    }

    public void loginRequest() {

        String user = etUser.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(user) && TextUtils.isEmpty(password)){
            Toast.makeText(mContext, "Llenar ambos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        final LoginUser loginUser = new LoginUser(user, password);

        UserLoginAuthService authService = APICodo.requestToken().create(UserLoginAuthService.class);
        Call<TokenResponse> call = authService.authGetToken(loginUser);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveDataAndStartSession(loginUser.getUser(), response.body());
                    Log.d(TAG, response.body().toString());
                } else {
                    Toast.makeText(mContext, "Usuario no autorizado", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Bad user");
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(mContext, "Error al validar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveDataAndStartSession(String userName, TokenResponse tokenResponse) {
        //Guarda la sesion del usuario; el usuario ahora esta logeado.
        String user = userName;
        String token = tokenResponse.getToken();

        mSesionApp.crearSesionLogin(user, token);

        startActivity(new Intent(mContext, RutasListaActivity.class));
        finish();
    }
}
