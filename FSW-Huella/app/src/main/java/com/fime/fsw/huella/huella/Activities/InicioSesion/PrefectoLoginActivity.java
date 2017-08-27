package com.fime.fsw.huella.huella.Activities.InicioSesion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fime.fsw.huella.huella.Activities.AuthDownloadActivity;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

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

    public void initComponentes() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.prefecto_login_titulo));
            getSupportActionBar().hide();
        }

        btnIniciarSesion = (Button) findViewById(R.id.iniciar_sesion_button);
        etUser = (EditText)findViewById(R.id.user_edittext);
        etPassword = (EditText)findViewById(R.id.password_edittext);

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
    }

    public void loginRequest() {

        String user = etUser.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(user) && TextUtils.isEmpty(password)){
            Toast.makeText(mContext, "Llenar ambos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(mContext, AuthDownloadActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

}
