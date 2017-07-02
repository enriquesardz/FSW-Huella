package com.ensardz.huellaapitest.Database;

import android.provider.BaseColumns;

/**
 * Created by ensardz on 23/06/2017.
 */

public class HuellaContract{

    private HuellaContract(){}

    public static class HuellaEntry implements BaseColumns {
        public static final String TASK_TABLA_NOMBRE = "task";
        public static final String COLUMNA_ROOM = "room";
        public static final String COLUMNA_ASSIGNMENT = "assignmnet";
        public static final String COLUMNA_ACADEMY_HOUR = "academyHour";
        public static final String COLUMNA_BARCODE = "barcode";
        public static final String COLUMNA_EMPLOYEE_NUMBER = "employeeNumber";
        public static final String COLUMNA_EMPLOYEE_NAME = "name";
        public static final String COLUMNA_EMPLOYEE_FULLNAME = "fullName";
        public static final String COLUMNA_HEXCODE = "hexCode";
    }
}