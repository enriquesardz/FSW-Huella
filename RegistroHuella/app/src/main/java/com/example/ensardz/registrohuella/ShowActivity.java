package com.example.ensardz.registrohuella;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ensardz.registrohuella.Adaptador.EmpleadoCursorAdapter;
import com.example.ensardz.registrohuella.Datos.HuellaContract;
import com.example.ensardz.registrohuella.Datos.HuellaDBHelper;

public class ShowActivity extends AppCompatActivity {

    ListView listView;
    HuellaDBHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        helper = new HuellaDBHelper(this);
        db = helper.getReadableDatabase();
        cursor = null;

        try {

            String[] projection = {
                    HuellaContract.HuellaEntry._ID,
                    HuellaContract.HuellaEntry.COLUMNA_NUMERO_EMPLEADO,
                    HuellaContract.HuellaEntry.COLUMNA_NOMBRE
            };

            cursor = db.query(
                    HuellaContract.HuellaEntry.TABLA_USUARIO_NOMBRE,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            listView = (ListView) findViewById(R.id.list_view);
            EmpleadoCursorAdapter adapter = new EmpleadoCursorAdapter(this, cursor);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (helper != null) {
            helper.close();
        }
        if (db != null) {
            db.close();
        }
        if (cursor != null) {
            db.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
