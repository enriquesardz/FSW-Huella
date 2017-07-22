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
    public static final String KEY_USUARIO = "usuario";
    public static final String KEY_USER_TOKEN = "userToken";
    public static final String KEY_CURRENT_ITEM_LISTA = "currentItem";
    public static final String KEY_LAST_ITEM_LISTA = "lastItem";

    public SesionAplicacion(Context context){
        mContext = context;
        preferences = mContext.getSharedPreferences(PREF_NOMBRE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
    }
    //Crea sesion de Log In, se salta la pantalla de login
    //y lo manda a descargar
    public void crearSesionLogin(String usuario, String userToken){
        editor.putBoolean(KEY_LOGIN, true);
        editor.putString(KEY_USUARIO, usuario);
        editor.putString(KEY_USER_TOKEN, userToken);
        editor.commit();
    }
    public void setCurrentTaskPosition(long currentTaskPosition){
        editor.putLong(KEY_CURRENT_ITEM_LISTA, currentTaskPosition);
        editor.commit();
    }

    public long getCurrentTaskPosition(){
        return preferences.getLong(KEY_CURRENT_ITEM_LISTA, -1);
    }

    public void setLastTaskPosition(long lastTaskPosition){
        editor.putLong(KEY_LAST_ITEM_LISTA, lastTaskPosition);
        editor.commit();
    }

    public long getLastTaskPosition(){
        return preferences.getLong(KEY_LAST_ITEM_LISTA, -1);
    }

    //Borra todo, es decir, se termina la sesion de descarga y login
    public void terminarSesionAplicacion(){
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getDetalleUsuario(){
        HashMap<String, String> usuario = new HashMap<String,String>();
        usuario.put(KEY_USUARIO, preferences.getString(KEY_USUARIO,null));
        usuario.put(KEY_USER_TOKEN, preferences.getString(KEY_USER_TOKEN,null));
        return usuario;
    }

    public boolean usuarioLogeado(){
        return preferences.getBoolean(KEY_LOGIN, false);
    }
}
