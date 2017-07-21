package com.fime.fsw.huella.huella.UI.RecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Quique on 07/07/2017.
 */

public class RecorridoAdapter extends RealmRecyclerViewAdapter<Task, RecorridoAdapter.ViewHolder>{

    Context mContext;
    RecyclerViewItemClickListener mListener;

    long currentTask;

    public RecorridoAdapter(Context context, @Nullable OrderedRealmCollection<Task> data, long currentTask, RecyclerViewItemClickListener listener) {
        super(data, true);
        setHasStableIds(true);

        this.mContext = context;
        this.mListener = listener;
        this.currentTask = currentTask;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvHoraFime;
        public TextView tvSalonFime;
        public View listItemContainer;
        public CardView cardViewContainer;
        public Task task;

        public ViewHolder(View v){
            super(v);
            tvHoraFime = (TextView) v.findViewById(R.id.hora_fime_textview);
            tvSalonFime = (TextView) v.findViewById(R.id.salon_fime_textview);
            listItemContainer = v.findViewById(R.id.list_item_container);
            //  Card view para desactivar el onClick
            cardViewContainer = (CardView)v.findViewById(R.id.cardview_container);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recorrido_actual, parent, false);
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
        final Task task = getItem(position);
        holder.task = task;

        int taskState = task.getTaskState();

        //TODO: Cambiar hora hardcoded
        holder.tvHoraFime.setText("Hard");
        holder.tvSalonFime.setText(task.getRoom().getRoomNumber());

        //Si el task no ha pasado, entonces se desactiva
        if(task.getSequence() > currentTask) {
            holder.cardViewContainer.setClickable(false);
            holder.cardViewContainer.setActivated(false);
            holder.listItemContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_disabled));
        } else{
            holder.cardViewContainer.setClickable(true);
            holder.cardViewContainer.setActivated(true);
            holder.listItemContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.fime_mint));
        }

        switch (taskState){
            case Task.STATE_NO_HA_PASADO:
                holder.tvHoraFime.setTextColor(ContextCompat.getColor(mContext, R.color.primary_text));
                holder.tvSalonFime.setTextColor(ContextCompat.getColor(mContext, R.color.primary_text));
                break;
            case Task.STATE_PASO_VINO_MAESTRO:
                holder.listItemContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.verde_obscuro));
                holder.tvHoraFime.setTextColor(ContextCompat.getColor(mContext, R.color.blanco));
                holder.tvSalonFime.setTextColor(ContextCompat.getColor(mContext, R.color.blanco));
                break;
            case Task.STATE_PASO_NO_VINO_MAESTRO:
                holder.listItemContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.verde_obscuro));
                holder.tvHoraFime.setTextColor(ContextCompat.getColor(mContext, R.color.rojo_error));
                holder.tvSalonFime.setTextColor(ContextCompat.getColor(mContext, R.color.rojo_error));
                break;
            default:
                break;
        }

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
    public Task getItem(int index) {
        return super.getItem(index);
    }

    @Nullable
    @Override
    public OrderedRealmCollection<Task> getData() {
        return super.getData();
    }

    @Override
    public void updateData(@Nullable OrderedRealmCollection<Task> data) {
        super.updateData(data);
    }

}
