package com.ensardz.fsw.milista;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ensardz on 10/11/17.
 */

public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder> {

    ArrayList<String> miData;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View view;

        public ViewHolder(View view){
            super(view);
            this.view = view;
        }
    }

    public MiAdaptador(ArrayList<String> data){
        miData = data;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TextView tv = holder.view.findViewById(R.id.texto);
        tv.setText(miData.get(position));

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.view.getContext(), tv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comida_list_item, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public int getItemCount() {
        return miData.size();
    }
}
