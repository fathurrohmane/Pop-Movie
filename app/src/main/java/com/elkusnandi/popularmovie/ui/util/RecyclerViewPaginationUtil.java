package com.elkusnandi.popularmovie.ui.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Taruna 98 on 3/6/2018.
 */

public class RecyclerViewPaginationUtil extends RecyclerView.OnScrollListener {

    private GridLayoutManager layoutManager;
    private ScrollListener scrollListener;
    private int currentPage = 0;
    private int itemPerPage = 10;
    private int threshold = 2;

    public RecyclerViewPaginationUtil(ScrollListener scrollListener, GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        this.scrollListener = scrollListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if ((visibleItemCount + firstVisibleItemPosition - threshold) >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= itemPerPage) {
            scrollListener.onPageLoaded();
        }

    }

    public interface ScrollListener {
        void onPageLoaded();
    }
}
