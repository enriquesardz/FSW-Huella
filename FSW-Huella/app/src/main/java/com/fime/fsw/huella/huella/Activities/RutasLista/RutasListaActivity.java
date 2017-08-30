package com.fime.fsw.huella.huella.Activities.RutasLista;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICallbackListener;
import com.fime.fsw.huella.huella.API.APIManager;
import com.fime.fsw.huella.huella.Activities.InicioSesion.PrefectoLoginActivity;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Checkout;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Owner;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

public class RutasListaActivity extends AppCompatActivity {

    private static final String TAG = APP_TAG + RutasListaActivity.class.getSimpleName();
    private static final String HUELLA_ENRIQUE = "03015B1C0000C002C002C0008000800080008000800080008000800000008000800080008002C00200000000000000000000000000000000239013BE3614137E561B16DE2520915E3DA4909E0C2E263E23304DBE153C0B1E591F441F38AA4E3F152F0E5F48B249DF53B348BF3DB44B5F5A39087F5F3F1EFF19BFC91F4587561C73B9A07C678557DD6708C1BD4109803D7235DED556BCDF9D4A2C0C3A4FAD093A2FC0DEFB353FE0F70000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003015D1C00008002800000000000000000000000000000000000000000000000000080008000C002000000000000000000000000000000002310149E379413DE561B16DE2520917E3DA4909E23304DDE73C39F9E591F441F38AA4E3F152ECE9F48B249DF53B3489F3DB44B5F7235DEDF5A38C89F723A9FF74607561C4A2C0C3C4FAD88FC153C4B1C680557DD6688821D4109805D58BEDF3D19BF895D5FBF9EFD353FE0FB2FC0DEDB00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

    Context mContext;
    SesionAplicacion mSesionApp;
    Realm mRealm;

    TextView tvResponse, tvDia;
    BottomBar mBarraNav;
    LinearLayout frameLayoutContainer, emptyStateContainer, loadingState;
    com.getbase.floatingactionbutton.FloatingActionButton btnCerrarSesion, btnUpdate;

    private RelativeLayout fondoOpaco;
    private FloatingActionsMenu floatingActionsMenu;

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

        tvResponse = (TextView) findViewById(R.id.dia_textview);
        tvDia = (TextView) findViewById(R.id.dia_textview);
        btnCerrarSesion = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.close_session_button);
        btnUpdate = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.update_button);
        frameLayoutContainer = (LinearLayout) findViewById(R.id.framelayout_container);
        emptyStateContainer = (LinearLayout) findViewById(R.id.empty_state);
        loadingState = (LinearLayout) findViewById(R.id.loading_state);
        
        setFloatingButtonControls();

        boolean yaDescargo = getIntent().getBooleanExtra("yaDescargo", false);

        Route route = RealmProvider.getRoute(mRealm);

        if (yaDescargo || route != null) {
            loadFragmentAndNavBar();
            tvDia.setText(route.getDay());
        } else {
            showEmptyState();
        }

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSesionApp.terminarSesionAplicacion();
                //TODO: Quitar como comentario
                //                RealmProvider.dropAllRealmTables(mRealm);
                floatingActionsMenu.collapse();
                startActivity(new Intent(mContext, PrefectoLoginActivity.class));
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                startRouteAndTasksDownload();
            }
        });


    }

    public void startRouteAndTasksDownload() {

        showLoadingState();

        HashMap<String, String> userData = mSesionApp.getDetalleUsuario();
        String jwtToken = userData.get(SesionAplicacion.KEY_USER_TOKEN);

        APIManager.getInstance().startRouteAndTasksDownload(jwtToken, new APICallbackListener<List<Route>>() {
            @Override
            public void response(List<Route> routes) {
                if (!routes.isEmpty()) {
                    //Guarda los datos al Realm
                    RealmProvider.saveRouteListWTasksToRealm(mRealm, routes);
                    loadFragmentAndNavBar();
                } else {
                    //No regreso nada y tampoco guardo a Realm, asi que se inicia
                    //la siguiente actividad con un empty state
                    showEmptyState();
                }
            }

            @Override
            public void failure() {
                Toast.makeText(mContext, "Fallo en la descarga", Toast.LENGTH_SHORT).show();
                showEmptyState();
            }
        });
    }

    public void loadFragmentAndNavBar() {

        mBarraNav = (BottomBar) findViewById(R.id.barra_navegacion);
        setUpBarraNavegacion();
        mBarraNav.selectTabWithId(R.id.tab_todo_routes);

        showFrameLayout();

    }

    private void setFloatingButtonControls() {
        fondoOpaco = (RelativeLayout) findViewById(R.id.fondoOpaco);
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

/*    public void loadFragmentAndNavBar() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvRutas.setHasFixedSize(true);
        rvRutas.setLayoutManager(linearLayoutManager);

//        fixRoutesAndTasks();

        OrderedRealmCollection<Route> orderedRoutes = RealmProvider.getAllOrderedRoutes(mRealm);

        //Muestra el dia que se descargaron las rutas.
        tvResponse.setText(orderedRoutes.get(0).getDay());

        rvRutasAdapter = new RutasRecyclerViewAdapter(mContext, orderedRoutes, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Route route = rvRutasAdapter.getItem(position);
                startRecorridoActivity(route);
                Log.d(TAG, route.toString());
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Route route = rvRutasAdapter.getItem(position);
                showUploadRouteMessage(route);
            }
        });

        rvRutas.setAdapter(rvRutasAdapter);

        showFrameLayout();
    }

    public void startRecorridoActivity(Route route) {
        Intent intent = new Intent(mContext, RecorridoMainActivity.class);
        String routeId = route.get_id();

        //Guarda el ID de la ruta que se selecciona
        mSesionApp.crearSesionRutaSeleccionada(routeId);
        startActivity(intent);

        finish();
    }

    public void showUploadRouteMessage(final Route route) {
        new AlertDialog.Builder(mContext)
                .setMessage("¿Subir ruta?")
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tryToUploadRoute(route);
                            }
                        })
                .setNegativeButton("No", null)
                .show();
    }

    public void tryToUploadRoute(final Route route) {

        HashMap<String, String> userData = mSesionApp.getDetalleUsuario();
        String jwtToken = userData.get(SesionAplicacion.KEY_USER_TOKEN);

        if (!route.isCompleted()) {
            Toast.makeText(mContext, "No ha terminado esta ruta.", Toast.LENGTH_SHORT).show();
            return;
        } else if (route.isWasUploaded()) {
            Toast.makeText(mContext, "Esta ruta ya fue subida.", Toast.LENGTH_SHORT).show();
            return;
        }

        UploadCheckouts uploadCheckouts = getCheckoutsFromRealm(route);

        APIServices service = APICodo.uploadCheckouts().create(APIServices.class);
        Call<UploadResponse> call = service.subirCheckoutsRuta(jwtToken, uploadCheckouts);

        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                UploadResponse uploadResponse = response.body();
                if (response.isSuccessful() && uploadResponse != null) {
                    if (TextUtils.equals(uploadResponse.getStatus().toLowerCase().trim(), "success")) {
                        RealmProvider.setRouteWasUploaded(mRealm, route);
                    } else {
                        Toast.makeText(mContext, uploadResponse.getMessages(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //No regreso nada y tampoco guardo a Realm, asi que se inicia
                    //la siguiente actividad con un empty state
                    Toast.makeText(mContext, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Toast.makeText(mContext, "Error: Error de red.", Toast.LENGTH_SHORT).show();
            }
        });

            public UploadCheckouts getCheckoutsFromRealm(Route route) {

        UploadCheckouts uploadCheckouts;

        List<Task> tasks = RealmProvider.getRouteCheckoutsFromRealm(mRealm, route);

        uploadCheckouts = UploadCheckouts.create(route.get_id(), tasks);

        return uploadCheckouts;
    }

    }*/

    public void fixRoutesAndTasks() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<Route> routes = mRealm.where(Route.class).findAll();
                List<Owner> owners = mRealm.where(Owner.class).findAll();

                mRealm.delete(Checkout.class);

                for (Route route : routes) {

                    List<Task> tasks = route.getTasks();
                    route.setCompleted(false);
                    route.setCurrentTask(0);
                    route.setLastTask(tasks.size() - 1);
                    route.setWasUploaded(false);

                    int i = 0;

                    for (Task task : tasks) {
                        task.setSequence(i);
                        task.setCheckout(mRealm.copyToRealm(new Checkout()));
                        task.setTaskState(Task.STATE_NO_HA_PASADO);
                        i++;
                    }

                }

                for (Owner owner : owners) {
                    owner.setFingerPrint(HUELLA_ENRIQUE);
                }
            }
        });
    }

}
