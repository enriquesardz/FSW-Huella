package com.fime.fsw.huella.huella.Utilidad;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Objetos.RecorridoActualItem;

import java.util.ArrayList;

/**
 * Created by Quique on 11/06/2017.
 */

public class RecorridoActualAdapter extends RecyclerView.Adapter<RecorridoActualAdapter.ViewHolder>{
    ArrayList<RecorridoActualItem> mData;
    Context mContext;
    RecyclerViewItemClickListener mListener;

    public RecorridoActualAdapter(Context context, ArrayList<RecorridoActualItem> data, RecyclerViewItemClickListener listener){
        this.mContext = context;
        this.mData = data;
        this.mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTVHoraFime;
        public TextView mTVSalonFime;
        public View listItemContainer;

        public ViewHolder(View v){
            super(v);
            mTVHoraFime = (TextView)v.findViewById(R.id.hora_fime_textview);
            mTVSalonFime = (TextView)v.findViewById(R.id.salon_fime_textview);
            listItemContainer = v.findViewById(R.id.list_item_container);
        }
    }
    public RecorridoActualAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recorrido_actual, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int taskState = mData.get(position).getRecorridoState();
        switch (taskState){
            case 0:
                holder.listItemContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.fime_mint));
                break;
            case 1:
                holder.listItemContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.verde_obscuro));
                holder.mTVHoraFime.setTextColor(ContextCompat.getColor(mContext, R.color.blanco));
                holder.mTVSalonFime.setTextColor(ContextCompat.getColor(mContext, R.color.blanco));
                break;
            case 2:
                holder.listItemContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.verde_obscuro));
                holder.mTVHoraFime.setTextColor(ContextCompat.getColor(mContext, R.color.rojo_error));
                holder.mTVSalonFime.setTextColor(ContextCompat.getColor(mContext, R.color.rojo_error));
                break;
            default:
                break;
        }
        holder.mTVHoraFime.setText(mData.get(position).getHoraFime());
        holder.mTVSalonFime.setText(mData.get(position).getSalonFime());
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }
}