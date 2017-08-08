package com.fime.fsw.huella.huella.Utilidad;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by ensardz on 14/06/2017.
 */

public class SesionAplicacion {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context mContext;

    public static final String PREF_NOMBRE = "com.fime.fsw.huella.AndroidFSWHuella";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_ROUTE_ID = "routeId";
    public static final String KEY_ROUTE_IS_SELECTED = "routeIsSelected";
    public static final String KEY_USUARIO = "usuario";
    public static final String KEY_USER_TOKEN = "userToken";
    public static final String KEY_REFRESH_TOKEN = "refreshToken";

    public SesionAplicacion(Context context){
        mContext = context;
        preferences = mContext.getSharedPreferences(PREF_NOMBRE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
    }
    //Crea sesion de Log In, se salta la pantalla de login y salta
    //a la Lista de Rutas
    public void crearSesionLogin(String usuario, String userToken, String refreshToken){
        editor.putBoolean(KEY_LOGIN, true);
        editor.putString(KEY_USUARIO, usuario);
        editor.putString(KEY_USER_TOKEN, userToken);
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.commit();
    }

    //Inicia sesion de ruta seleccionada para que salte directo
    //a RecorridoMain
    public void crearSesionRutaSeleccionada(String routeID){
        editor.putBoolean(KEY_ROUTE_IS_SELECTED, true);
        editor.putString(KEY_ROUTE_ID, routeID);
        editor.commit();
    }

    public String getCurrentRutaId(){
        return preferences.getString(KEY_ROUTE_ID, null);
    }

    public void dropRutaSeleccionada(){
        editor.putBoolean(KEY_ROUTE_IS_SELECTED, false);
        editor.putString(KEY_ROUTE_ID, null);
        editor.commit();
    }

    //Borra all, es decir, se termina la sesion de descarga y login
    public void terminarSesionAplicacion(){
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getDetalleUsuario(){
        HashMap<String, String> usuario = new HashMap<String,String>();

        usuario.put(KEY_USUARIO, preferences.getString(KEY_USUARIO,null));
        usuario.put(KEY_USER_TOKEN, preferences.getString(KEY_USER_TOKEN,null));
        usuario.put(KEY_REFRESH_TOKEN, preferences.getString(KEY_REFRESH_TOKEN, null));

        return usuario;
    }

    public boolean usuarioLogeado(){
        return preferences.getBoolean(KEY_LOGIN, false);
    }

    public boolean routeIsSelected(){
        return preferences.getBoolean(KEY_ROUTE_IS_SELECTED, false);
    }
}
