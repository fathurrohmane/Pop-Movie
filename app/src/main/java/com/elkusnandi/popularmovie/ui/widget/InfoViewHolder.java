package com.elkusnandi.popularmovie.ui.widget;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elkusnandi.popularmovie.R;

/**
 * Created by Taruna 98 on 3/10/2018.
 */

public class InfoViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewInfo;
    public Button reloadButton;
    public ContentLoadingProgressBar progressBar;

    public InfoViewHolder(View itemView) {
        super(itemView);

        textViewInfo = itemView.findViewById(R.id.text_view_info);
        reloadButton = itemView.findViewById(R.id.button_reload);
        progressBar = itemView.findViewById(R.id.progress_bar);
    }

    public void setReloadButtonOnClickListener(View.OnClickListener onClickListener) {
        reloadButton.setOnClickListener(onClickListener);
    }
}