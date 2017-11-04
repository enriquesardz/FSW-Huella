package com.fime.fsw.huella.huella.Activities.InicioSesion;

/**
 * Created by ensardz on 03/11/2017.
 * Esta clase se encarga de autorizar el inicio de sesion del prefecto, se revisan dos cosas:
 * que se halla descargado ya la tabla de prefectos, si esta descargada entonces compara las credenciales
 * con la tabla y logea al prefecto.
 */

public class LoginAuth {

    public interface onLoginAuthResponse {
        public void onSucess();
        public void onFail();
    }

    
}
