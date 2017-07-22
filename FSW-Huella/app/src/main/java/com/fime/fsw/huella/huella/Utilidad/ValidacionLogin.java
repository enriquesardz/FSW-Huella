package com.fime.fsw.huella.huella.Utilidad;

import android.content.Context;

/**
 * Created by ensardz on 14/06/2017.
 */

public class ValidacionLogin {
    private Context mContext;
    private String mUsuario;
    private SesionAplicacion sesionAplicacion;

    public ValidacionLogin(Context context, String usuario){
        mContext = context;
        mUsuario = usuario;
        sesionAplicacion = new SesionAplicacion(mContext);
        this.validarUsuario(usuario);
    }

    private void validarUsuario(String usuario){
        //TODO: Pasos para validar y despues crea la sesion
//        sesionAplicacion.crearSesionLogin(usuario);
    }
}
