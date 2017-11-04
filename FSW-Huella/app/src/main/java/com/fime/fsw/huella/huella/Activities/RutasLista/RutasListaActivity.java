package com.fime.fsw.huella.huella.Activities.RutasLista;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICallbackListener;
import com.fime.fsw.huella.huella.API.APIManager;
import com.fime.fsw.huella.huella.API.Deserializadores.GroupsDeserializer;
import com.fime.fsw.huella.huella.API.Deserializadores.PrefectosDeserializer;
import com.fime.fsw.huella.huella.Activities.InicioSesion.PrefectoLoginActivity;
import com.fime.fsw.huella.huella.BuildConfig;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Grupo;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Prefecto;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;
import com.fime.fsw.huella.huella.Data.Modelos.UploadCheckouts;
import com.fime.fsw.huella.huella.Data.Modelos.UploadResponse;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.AsyncTaskResponseListener;
import com.fime.fsw.huella.huella.Utilidad.RouteAndTaskGenerator;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

public class RutasListaActivity extends AppCompatActivity {

    private static final String TAG = APP_TAG + RutasListaActivity.class.getSimpleName();
    private static final String HUELLA_ENRIQUE = "03015B1C0000C002C002C0008000800080008000800080008000800000008000800080008002C00200000000000000000000000000000000239013BE3614137E561B16DE2520915E3DA4909E0C2E263E23304DBE153C0B1E591F441F38AA4E3F152F0E5F48B249DF53B348BF3DB44B5F5A39087F5F3F1EFF19BFC91F4587561C73B9A07C678557DD6708C1BD4109803D7235DED556BCDF9D4A2C0C3A4FAD093A2FC0DEFB353FE0F70000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003015D1C00008002800000000000000000000000000000000000000000000000000080008000C002000000000000000000000000000000002310149E379413DE561B16DE2520917E3DA4909E23304DDE73C39F9E591F441F38AA4E3F152ECE9F48B249DF53B3489F3DB44B5F7235DEDF5A38C89F723A9FF74607561C4A2C0C3C4FAD88FC153C4B1C680557DD6688821D4109805D58BEDF3D19BF895D5FBF9EFD353FE0FB2FC0DEDB00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

    private Context mContext;
    private SesionAplicacion mSesionApp;
    private Realm mRealm;

    private TextView tvResponse, tvDia;
    private BottomBar mBarraNav;
    private LinearLayout frameLayoutContainer, emptyStateContainer, loadingState;
    private com.getbase.floatingactionbutton.FloatingActionButton btnCerrarSesion, btnUpdate, btnSubirRutas;

    private View fondoOpaco;
    private FloatingActionsMenu floatingActionsMenu;

    private List<Grupo> listaGrupos;
    private List<Prefecto> listaPrefectos;

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                if (BuildConfig.DEBUG) {
                    getGroupsOffline();
                } else {
                    getGroups();
                }
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

    public void getGroups() {
        showLoadingState();

        APIManager.getInstance().downloadPrefectos(new APICallbackListener<List<Prefecto>>() {
            @Override
            public void response(List<Prefecto> response) {

                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DATE, 1);
                today = c.getTime();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String todayDate = format.format(today);
                Log.i(TAG, todayDate);

                listaPrefectos = response;

                APIManager.getInstance().downloadGrupos("", todayDate, new APICallbackListener<List<Grupo>>() {
                    @Override
                    public void response(List<Grupo> response) {

                        listaGrupos = response;
                        saveGroupsAndPrefectosToRealm();

                    }

                    @Override
                    public void failure() {
                        Toast.makeText(mContext, "No descargo.", Toast.LENGTH_SHORT).show();
                        showEmptyState();
                    }
                });
            }

            @Override
            public void failure() {
                Toast.makeText(mContext, "No descargo.", Toast.LENGTH_SHORT).show();
                showEmptyState();
            }
        });
    }

    //TODO: Mover a clase aparte
    public void saveGroupsAndPrefectosToRealm() {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Grupo grupo : listaGrupos) {
                    realm.copyToRealmOrUpdate(grupo);
                }

                for (Prefecto prefecto : listaPrefectos) {
                    realm.copyToRealmOrUpdate(prefecto);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Crear Rutas y Tasks
                generateRoutesAndTasks();
            }
        });
    }

    public void generateRoutesAndTasks() {
        RouteAndTaskGenerator.getInstance(mRealm).createRoutesAndTasks();
        loadRouteFragments();
    }

    public void subirRutas() {
        //Checar si hay tareas por subir
        RealmResults<Task> doneTasks = mRealm.where(Task.class).notEqualTo(Task.TASK_STATE_FIELD, Task.STATE_NO_HA_PASADO).equalTo(Task.IS_UPLOADED_FIELD, false).findAllSorted(Task.ROUTE_ID_FIELD);

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

    public void getGroupsOffline() {

        showLoadingState();

        new SaveGroupAndPrefectoAsyncClass(new AsyncTaskResponseListener() {
            @Override
            public void onSuccess() {
                saveGroupsAndPrefectosToRealm();
            }

            @Override
            public void onFailure() {
                showEmptyState();
            }

        }).execute();

    }

    public String getJsonFromGroupsFile() {
        String json = null;
        try {
            InputStream is = getAssets().open("groups.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
    }

    public String getJsonFromPrefectosFile() {
        String json = null;
        try {
            InputStream is = getAssets().open("prefectos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
    }

    public class SaveGroupAndPrefectoAsyncClass extends AsyncTask<String, Integer, String> {

        AsyncTaskResponseListener listener;

        public SaveGroupAndPrefectoAsyncClass(AsyncTaskResponseListener asyncTaskResponseListener) {
            super();
            listener = asyncTaskResponseListener;
        }

        @Override
        protected String doInBackground(String... params) {

            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String todayDate = format.format(today);
            Log.i(TAG, todayDate);

            String jsonGroups = getJsonFromGroupsFile();
            String jsonPrefectos = getJsonFromPrefectosFile();


            Type listGrupos = new TypeToken<List<Grupo>>() {
            }.getType();
            Type listPrefectos = new TypeToken<List<Prefecto>>() {
            }.getType();

            GsonBuilder groupGson = new GsonBuilder()
                    .serializeNulls()
                    .registerTypeAdapter(listGrupos, new GroupsDeserializer());

            GsonBuilder prefectoGson = new GsonBuilder()
                    .serializeNulls()
                    .registerTypeAdapter(listPrefectos, new PrefectosDeserializer());

            List<Grupo> grupos = groupGson.create().fromJson(jsonGroups, listGrupos);
            List<Prefecto> prefectos = prefectoGson.create().fromJson(jsonPrefectos, listPrefectos);

            if (grupos != null && prefectos != null) {

                Log.i(TAG, String.valueOf(grupos.size()));
                Log.i(TAG, String.valueOf(prefectos.size()));

                listaGrupos = grupos;
                listaPrefectos = prefectos;

                return "ok";
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (!TextUtils.isEmpty(s)) {
                //Ok
                listener.onSuccess();
            } else {
                listener.onFailure();
            }
        }


    }

}
