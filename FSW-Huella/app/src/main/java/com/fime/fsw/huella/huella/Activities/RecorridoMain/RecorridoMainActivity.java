package com.fime.fsw.huella.huella.Activities.RecorridoMain;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fime.fsw.huella.huella.Activities.HuellaApplication;
import com.fime.fsw.huella.huella.Activities.InicioSesion.MenuInicioSesionActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Checkout;
import com.fime.fsw.huella.huella.Data.Modelos.Owner;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.Fragments.DatosVisitaFragment;
import com.fime.fsw.huella.huella.Fragments.RecorridoActualFragment;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import io.realm.Realm;
import io.realm.RealmResults;


public class RecorridoMainActivity extends AppCompatActivity implements RecorridoActualFragment.OnFragmentInteractionListener, DatosVisitaFragment.OnFragmentInteractionListener {

    public static final String TAG = HuellaApplication.APP_TAG + RecorridoMainActivity.class.getSimpleName();
    public static final String KEY_ID_TASK = "_id";

    private BottomBar mBarraNav;
    private Fragment mFragment;
    private Context mContext;
    private Bundle mBundle;
    private Realm mRealm;
    private SesionAplicacion mSesionApp;

    private Button btnSubir;

    //RabbitMQ
    private BlockingDeque<String> queue = new LinkedBlockingDeque<String>();
    private ConnectionFactory mFactory = new ConnectionFactory();
    Thread publishThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido_main);

        mContext = RecorridoMainActivity.this;
        mSesionApp = new SesionAplicacion(mContext);
        mRealm = Realm.getDefaultInstance();

        getCheckoutsFromRealmToJson();
        setupConnectionFactory();
        publishToAMQP();

        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrar_sesion:
                mSesionApp.terminarSesionAplicacion();
                //TODO: Aqui se deben de regresar los checkouts al web service.
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setMessage(getResources().getString(R.string.mrecorrido_seguro_salir))
                .setPositiveButton(getResources().getString(R.string.mrecorrido_si),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RecorridoMainActivity.super.onBackPressed();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.mrecorrido_no), null)
                .show();
    }

    @Override
    public void onRecorridoActualItemSelected(Task task) {
        long id = task.get_id();
        mBundle.putLong(Task._ID_KEY, id);
        mBarraNav.selectTabWithId(R.id.tab_datos_visita);
    }

    @Override
    public void onCodigoBarrasFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        publishThread.interrupt();
    }

    public void initComponents() {
        mBundle = new Bundle();

        mBarraNav = (BottomBar) findViewById(R.id.barra_navegacion);
        btnSubir = (Button) findViewById(R.id.subida_test);

        mBarraNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_datos_visita) {
                    //Puede iniciar vacio o porque
                    //se le dio click a un salon del recorrido actual.
                    mFragment = new DatosVisitaFragment();
                    if (mBundle != null) {
                        mFragment.setArguments(mBundle);
                    }

                } else if (tabId == R.id.tab_recorrido_actual) {
                    mFragment = new RecorridoActualFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
            }
        });

        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishMessage();
            }
        });

        mBarraNav.selectTabWithId(R.id.tab_recorrido_actual);
    }

    //Agrega mensajes al queue local
    void publishMessage() {
        try {
            String jsonCheckouts = getCheckoutsFromRealmToJson();
            Log.d(TAG, jsonCheckouts);
            queue.putLast(jsonCheckouts);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setupConnectionFactory() {
        String URI = "amqp://tjttatfr:q2yhgpI_sIXXlQFiZUSAizmjH9I2vASr@wasp.rmq.cloudamqp.com/tjttatfr";
        try {
            mFactory.setAutomaticRecoveryEnabled(false);
            mFactory.setUri(URI);
        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void publishToAMQP() {
        publishThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Connection cn = mFactory.newConnection();
                        Channel ch = cn.createChannel();
                        ch.confirmSelect();

                        while (true) {
                            String message = queue.takeFirst();
                            try {
                                ch.basicPublish("", "checkouts", null, message.getBytes());
                                Log.i(TAG, "Se mando el mensaje: " + message);
                                ch.waitForConfirmsOrDie();
                            } catch (Exception e) {
                                queue.putFirst(message);
                                Log.e(TAG, "No se pudo mandar el mensaje: " + message);
                                throw e;
                            }
                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e1) {
                        Log.d(TAG, "Se rompio la conexion porque la UNI bloquea todo lo que se mueva y les vale queso");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
        });
        publishThread.start();
    }

    public String getCheckoutsFromRealmToJson() {

    }
}