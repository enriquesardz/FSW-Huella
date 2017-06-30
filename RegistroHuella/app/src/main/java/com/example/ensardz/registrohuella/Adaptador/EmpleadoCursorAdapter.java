package com.example.ensardz.registrohuella.Adaptador;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ensardz.registrohuella.Datos.HuellaContract;
import com.example.ensardz.registrohuella.R;

/**
 * Created by Quique on 27/06/2017.
 */

public class EmpleadoCursorAdapter extends CursorAdapter{

    public EmpleadoCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.usuario_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idEmpleado = view.findViewById(R.id.usuario_id);
        TextView numEmpleado = view.findViewById(R.id.numero_empleado);
        TextView nombreEmpleado = view.findViewById(R.id.nombre_empleado);

        long id = cursor.getLong(cursor.getColumnIndexOrThrow(HuellaContract.HuellaEntry._ID));
        String numero = cursor.getString(cursor.getColumnIndexOrThrow(HuellaContract.HuellaEntry.COLUMNA_NUMERO_EMPLEADO));
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow(HuellaContract.HuellaEntry.COLUMNA_NOMBRE));

        idEmpleado.setText(Long.toString(id));
        numEmpleado.setText(numero);
        nombreEmpleado.setText(nombre);
    }
}
