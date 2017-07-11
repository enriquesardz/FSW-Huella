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
    public static final String KEY_DESCARGA = "descarga";
    public static final String KEY_USUARIO = "usuario";
    public static final String KEY_CURRENT_ITEM_LISTA = "currentItem";

    public SesionAplicacion(Context context){
        mContext = context;
        preferences = mContext.getSharedPreferences(PREF_NOMBRE, mContext.MODE_PRIVATE);
        editor = preferences.edit();
    }
    //Crea sesion de Log In, se salta la pantalla de login
    //y lo manda a descargar
    public void crearSesionLogin(String usuario){
        editor.putBoolean(KEY_LOGIN, true);
        editor.putString(KEY_USUARIO, usuario);
        editor.commit();
    }
    //Sesion de descarga es decir, se llama despues que el usuario descarga los datos,
    //por lo tanto, ya no pregunta si desea descargar la proxima vez que inicie
    //la aplicacion.
    public void crearSesionDescarga(){
        editor.putBoolean(KEY_DESCARGA, true);
        editor.commit();
    }

    public void setCurrentItemLista(int itemLista){
        editor.putInt(KEY_CURRENT_ITEM_LISTA, itemLista);
        editor.commit();
    }

    public int getCurrentItemLista(){
        return preferences.getInt(KEY_CURRENT_ITEM_LISTA, -1);
    }

    //Borra todo, es decir, se termina la sesion de descarga y login
    public void terminarSesionAplicacion(){
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getDetalleUsuario(){
        HashMap<String, String> usuario = new HashMap<String,String>();
        usuario.put(KEY_USUARIO, preferences.getString(KEY_USUARIO,null));
        return usuario;
    }

    public boolean usuarioLogeado(){
        return preferences.getBoolean(KEY_LOGIN, false);
    }

    public boolean usuarioYaDescargo(){
        return preferences.getBoolean(KEY_DESCARGA, false);
    }
}
