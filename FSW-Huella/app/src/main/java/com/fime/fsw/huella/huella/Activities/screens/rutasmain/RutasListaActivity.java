package com.fime.fsw.huella.huella.Activities.screens.rutasmain;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fime.fsw.huella.huella.API.APICallbackListener;
import com.fime.fsw.huella.huella.API.APIManager;
import com.fime.fsw.huella.huella.Activities.screens.login.PrefectoLoginActivity;
import com.fime.fsw.huella.huella.BuildConfig;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;
import com.fime.fsw.huella.huella.Data.Modelos.UploadCheckouts;
import com.fime.fsw.huella.huella.Data.Modelos.UploadResponse;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

public class RutasListaActivity extends AppCompatActivity {

    private static final String TAG = APP_TAG + RutasListaActivity.class.getSimpleName();

    private Context mContext;
    private SesionAplicacion mSesionApp;
    private Realm mRealm;

    private TextView tvResponse, tvDia;
    private BottomBar mBarraNav;
    private LinearLayout frameLayoutContainer, emptyStateContainer, loadingState;
    private com.getbase.floatingactionbutton.FloatingActionButton btnCerrarSesion, btnUpdate, btnSubirRutas;

    private View fondoOpaco;
    private FloatingActionsMenu floatingActionsMenu;

    private String diaRutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas_lista);

        mContext = RutasListaActivity.this;
        mSesionApp = new SesionAplicacion(mContext);
        mRealm = Realm.getDefaultInstance();

        initComponentes();

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
                RealmProvider.dropAllRealmTables(mRealm);
                startActivity(new Intent(mContext, PrefectoLoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setMessage(getResources().getString(R.string.rutl_salir_aplicacion))
                .setPositiveButton(getResources().getString(R.string.mrecorrido_si),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RutasListaActivity.super.onBackPressed();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.mrecorrido_no), null)
                .show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    public void initComponentes() {

        tvDia = (TextView) findViewById(R.id.dia_textview);
        btnCerrarSesion = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.close_session_button);
        btnUpdate = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.update_button);
        btnSubirRutas = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.subir_button);
        frameLayoutContainer = (LinearLayout) findViewById(R.id.framelayout_container);
        emptyStateContainer = (LinearLayout) findViewById(R.id.empty_state);
        loadingState = (LinearLayout) findViewById(R.id.loading_state);

        setFloatingButtonControls();

        boolean yaDescargo = getIntent().getBooleanExtra("yaDescargo", false);

        Route route = RealmProvider.getRoute(mRealm);

        if (yaDescargo || route != null) {
            diaRutas = route.getDiaNombre();
            loadRouteFragments();
        } else {
            showEmptyState();
        }

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSesionApp.terminarSesionAplicacion();

                if (BuildConfig.DEBUG) {
                    RealmProvider.dropAllRealmTables(mRealm);
                }

                floatingActionsMenu.collapse();
                startActivity(new Intent(mContext, PrefectoLoginActivity.class));
                finish();
            }
        });

        btnSubirRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionsMenu.collapse();
                subirRutas();
            }
        });


    }

    public void loadRouteFragments() {
        Route route = RealmProvider.getRoute(mRealm);
        diaRutas = route.getDiaNombre();

        tvDia.setText(diaRutas);
        setUpBarraNavegacion();
        showFrameLayout();
    }

    private void setFloatingButtonControls() {
        fondoOpaco = findViewById(R.id.fondoOpaco);
        floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.floatingActionsMenu);
        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                fondoOpaco.setVisibility(RelativeLayout.VISIBLE);
                fondoOpaco.setClickable(true);
            }

            @Override
            public void onMenuCollapsed() {
                fondoOpaco.setVisibility(View.GONE);
            }
        });
    }

    private void setUpBarraNavegacion() {
        mBarraNav = (BottomBar) findViewById(R.id.barra_navegacion);
        mBarraNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment fragment = new Fragment();
                if (tabId == R.id.tab_todo_routes) {
                    fragment = new RutasARealizarFragment();
                } else if (tabId == R.id.tab_done_routes) {
                    fragment = new RutasTerminadasFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        mBarraNav.selectTabWithId(R.id.tab_todo_routes);
    }

    public void showEmptyState() {
        emptyStateContainer.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.VISIBLE);
        frameLayoutContainer.setVisibility(View.GONE);
        loadingState.setVisibility(View.GONE);
    }

    public void showFrameLayout() {
        frameLayoutContainer.setVisibility(View.VISIBLE);
        emptyStateContainer.setVisibility(View.GONE);
        loadingState.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
    }

    public void showLoadingState() {
        loadingState.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.GONE);
        frameLayoutContainer.setVisibility(View.GONE);
        emptyStateContainer.setVisibility(View.GONE);
    }

    public void subirRutas() {
        //Checar si hay tareas por subir
        RealmResults<Task> doneTasks = mRealm.where(Task.class)
                .notEqualTo(Task.TASK_STATE_FIELD, Task.STATE_NO_HA_PASADO)
                .equalTo(Task.IS_UPLOADED_FIELD, false).findAllSorted(Task.ROUTE_ID_FIELD);

        if (doneTasks.isEmpty()) {
            Log.d(TAG, "No hay tareas por subir");
            return;
        }

        //Si hay rutas terminadas sin subir
        List<Task> upDoneTasks = mRealm.copyFromRealm(doneTasks);

        UploadCheckouts upCheckouts = UploadCheckouts.create(upDoneTasks);

        APIManager.getInstance().uploadCheckouts(upCheckouts, new APICallbackListener<UploadResponse>() {
            @Override
            public void response(UploadResponse response) {
                Log.d(TAG, response.getStatus() + " " + response.getMessages());
            }

            @Override
            public void failure() {
                Log.i(TAG, "No se pudieron subir");
            }
        });
    }

}
