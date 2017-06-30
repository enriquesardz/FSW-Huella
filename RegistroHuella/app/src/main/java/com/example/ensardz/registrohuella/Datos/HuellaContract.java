package com.example.ensardz.registrohuella.Datos;

import android.provider.BaseColumns;

/**
 * Created by ensardz on 26/06/2017.
 */


public class HuellaContract{

    private HuellaContract(){}

    public static class HuellaEntry implements BaseColumns {
        public static final String TABLA_USUARIO_NOMBRE = "USER";
        public static final String COLUMNA_NUMERO_EMPLEADO = "EMPLOYEE_ID";
        public static final String COLUMNA_NOMBRE = "NAME";
        public static final String COLUMNA_HUELLA = "FINGERPRINT";
    }

}
