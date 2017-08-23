package com.example.ensardz.registrohuella.UI;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ensardz.registrohuella.Datos.Professor;
import com.example.ensardz.registrohuella.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by ensardz on 18/08/2017.
 */

public class ProfessorRecyclerViewAdapter extends RealmRecyclerViewAdapter<Professor, ProfessorRecyclerViewAdapter.ViewHolder>{

    Context mContext;
    RecyclerViewItemClickListener mListener;

    public ProfessorRecyclerViewAdapter(Context context, @Nullable OrderedRealmCollection<Professor> data, RecyclerViewItemClickListener listener) {
        super(data, true);
        setHasStableIds(true);

        this.mContext = context;
        this.mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvProfessor;
        public Professor professor;

        public ViewHolder(View v){
            super(v);
            tvProfessor = v.findViewById(R.id.professor_item_textview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_professor, parent, false);
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
        final Professor professor = getItem(position);
        holder.professor = professor;

        String rawName = professor.getRawName();

        holder.tvProfessor.setText(rawName);

        if(!TextUtils.isEmpty(professor.getFingerPrint())){
            holder.tvProfessor.setTextColor(Color.GREEN);
        } else {
            holder.tvProfessor.setTextColor(Color.BLACK);
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
    public Professor getItem(int index) {
        return super.getItem(index);
    }

    @Nullable
    @Override
    public OrderedRealmCollection<Professor> getData() {
        return super.getData();
    }

    @Override
    public void updateData(@Nullable OrderedRealmCollection<Professor> data) {
        super.updateData(data);
    }
}
