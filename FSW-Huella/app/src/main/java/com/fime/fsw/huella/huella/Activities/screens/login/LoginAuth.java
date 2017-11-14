package com.fime.fsw.huella.huella.Activities.screens.login;

import android.content.Context;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Prefecto;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import io.realm.Realm;

/**
 * Created by ensardz on 03/11/2017.
 * Esta clase se encarga de autorizar el inicio de sesion del prefecto, se revisan dos cosas:
 * que se halla descargado ya la tabla de prefectos, si esta descargada entonces compara las credenciales
 * con la tabla y logea al prefecto.
 */

public class LoginAuth {

    private Context context;
    private Realm realm;

    public interface onLoginAuthResponse {
        public void onLoginSuccess(String user, String password);

        public void onLoginFail();
    }

    public static LoginAuth getInstance(Context context, Realm realm){
        return new LoginAuth(context, realm);
    }

    private LoginAuth(Context context, Realm realm){
        this.context = context;
        this.realm = realm;
    }

    public void loginAttempt(String user, String password, onLoginAuthResponse callback){
        try {
            Prefecto prefecto = realm.where(Prefecto.class)
                    .equalTo(Prefecto.USUARIO_FIELD, user)
                    .equalTo(Prefecto.PASSWORD_FIELD, password)
                    .findFirst();

            if (prefecto == null) {
                callback.onLoginFail();
                return;
            }

            saveUserToken(user);
            callback.onLoginSuccess(user, password);

        } catch (Exception e) {
            e.printStackTrace();
            callback.onLoginFail();
        }

    }

    public void saveUserToken(String user) {
        SesionAplicacion sesionApp = new SesionAplicacion(context);
        sesionApp.crearSesionLogin(user,"token", "token");
    }
}
