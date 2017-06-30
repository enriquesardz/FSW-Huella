package com.fime.fsw.huella.huella.Data;

import android.provider.BaseColumns;

/**
 * Created by ensardz on 23/06/2017.
 */

public class HuellaContract{

    private HuellaContract(){}

    public static class HuellaEntry implements BaseColumns{
        public static final String TABLA_USUARIO_NOMBRE = "usuario";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_HUELLA = "huella";
    }

}
