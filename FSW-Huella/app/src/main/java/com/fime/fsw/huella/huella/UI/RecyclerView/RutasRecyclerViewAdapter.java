package com.fime.fsw.huella.huella.UI.RecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.R;

import org.w3c.dom.Text;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Quique on 22/07/2017.
 */

public class RutasRecyclerViewAdapter extends RealmRecyclerViewAdapter<Route, RutasRecyclerViewAdapter.ViewHolder> {
    Context mContext;
    RecyclerViewItemClickListener mListener;



    public RutasRecyclerViewAdapter(Context context, @Nullable OrderedRealmCollection<Route> data, RecyclerViewItemClickListener listener) {
        super(data, true);
        setHasStableIds(true);

        this.mContext = context;
        this.mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvRutaNum;
        public TextView tvTaskCount;
        public TextView tvRutaHora;

        public Route route;

        public ViewHolder(View v){
            super(v);
            tvRutaNum = (TextView) v.findViewById(R.id.ruta_num_textview);
            tvTaskCount = (TextView) v.findViewById(R.id.tasks_count_textview);
            tvRutaHora = (TextView) v.findViewById(R.id.ruta_hora_textview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ruta_lista, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Route route = getItem(position);
        holder.route = route;

        String routeNum =  String.valueOf(position + 1);
        String taskCount = String.valueOf(route.getTasksCount());
        String routeHora = String.valueOf(route.getAcademyHour());
        holder.tvRutaNum.setText(routeNum);
        holder.tvTaskCount.setText(taskCount);
        holder.tvRutaHora.setText(routeHora);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public long getItemId(int index) {
        return super.getItemId(index);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Nullable
    @Override
    public Route getItem(int index) {
        return super.getItem(index);
    }

    @Nullable
    @Override
    public OrderedRealmCollection<Route> getData() {
        return super.getData();
    }

    @Override
    public void updateData(@Nullable OrderedRealmCollection<Route> data) {
        super.updateData(data);
    }
}
