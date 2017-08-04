package com.fime.fsw.huella.huella.UI.RecyclerView;

import android.view.View;

/**
 * Created by Quique on 11/06/2017.
 */

public interface RecyclerViewItemClickListener {
    public void onItemClick(View v,int position);

    public void onItemLongClick(View v,int position);
}