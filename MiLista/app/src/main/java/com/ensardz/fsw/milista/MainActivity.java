package com.ensardz.fsw.milista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView miRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miRecyclerView = (RecyclerView) findViewById(R.id.mi_lista);

        ArrayList<String> comida = new ArrayList<>();

        comida.add("Hamburguesa");
        comida.add("Chocolate");
        comida.add("Frape");
        comida.add("Chile relleno");
        comida.add("Pollo");
        comida.add("Queso");

        MiAdaptador adapter = new MiAdaptador(comida);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        miRecyclerView.setLayoutManager(layoutManager);
        miRecyclerView.setAdapter(adapter);

    }
}
