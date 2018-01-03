package com.elkusnandi.popularmovie.common.interfaces;

import android.view.View;

/**
 * Created by Taruna 98 on 03/01/2018.
 */

public interface RecyclerViewItemClickListener<T> {

    void onItemClick(T item, View view);
}
