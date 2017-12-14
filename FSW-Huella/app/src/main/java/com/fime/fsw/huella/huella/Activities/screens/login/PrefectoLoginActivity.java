package com.fime.fsw.huella.huella.Activities.screens.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fime.fsw.huella.huella.Activities.screens.descarga.PrefectoDownloadActivity;
import com.fime.fsw.huella.huella.Activities.screens.rutasmain.RutasListaActivity;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.fime.fsw.huella.huella.tests.KotlinTest;

import io.realm.Realm;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

public class PrefectoLoginActivity extends AppCompatActivity implements LoginAuth.onLoginAuthResponse{

    private static final String TAG = APP_TAG + PrefectoLoginActivity.class.getSimpleName();

    Realm mRealm;

    private Button btnIniciarSesion, btnDescargar;
    private EditText etUser,etPassword;

    private Context mContext;
    private SesionAplicacion mSesionApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefecto_login);

        mContext = PrefectoLoginActivity.this;
        mSesionApp = new SesionAplicacion(mContext);
        mRealm = Realm.getDefaultInstance();

        initComponentes();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    public void initComponentes() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.prefecto_login_titulo));
            getSupportActionBar().hide();
        }

        btnIniciarSesion = (Button) findViewById(R.id.iniciar_sesion_button);
        etUser = (EditText)findViewById(R.id.user_edittext);
        etPassword = (EditText)findViewById(R.id.password_edittext);
        btnDescargar = (Button) findViewById(R.id.descargar_rutas);

        String user = getIntent().getStringExtra("user");
        if (!TextUtils.isEmpty(user)){
            etUser.setText(user);
        }

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownloadActivity();
            }
        });
    }

    public void loginRequest() {

        String user = etUser.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "Llenar ambos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginAuth.getInstance(mContext, mRealm)
                .loginAttempt(user, password, this);

    }

    @Override
    public void onLoginSuccess(String user, String password) {
        Intent intent = new Intent(mContext, RutasListaActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFail() {
        //El prefecto no se pudo logear
        Toast.makeText(mContext, "Descargue las rutas primero.", Toast.LENGTH_SHORT).show();
    }

    public void startDownloadActivity(){
        Intent intent = new Intent(mContext, PrefectoDownloadActivity.class);
        startActivity(intent);
    }
}
