package com.elkusnandi.popularmovie.ui.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Class to handle pagination for recyclerview
 *
 * @author Taruna 98 on 3/8/2018.
 */

public class PaginationUtil extends RecyclerView.OnScrollListener {

    private int itemPerPage;
    private int lastLoadedPage = 1;
    private int currentlyDisplayedPage = -1;
    private int pagePadding;
    private int pageOffset;

    private final GridLayoutManager layoutManager;
    private final ScrollListener scrollListener;

    /**
     * constructor
     *
     * @param scrollListener a callback
     * @param layoutManager  a GridLayoutManager to get access item data
     *                       (number of item, which is first visible, etc) in a recyclerview
     */
    public PaginationUtil(ScrollListener scrollListener, GridLayoutManager layoutManager) {
        super();
        this.layoutManager = layoutManager;
        this.scrollListener = scrollListener;
    }

    /**
     * Set page settings
     *
     * @param itemPerPage   the number of how many item per page in a data
     * @param pageStartFrom the number of where the page start from (default is 0)
     */
    public void setPageSettings(int itemPerPage, int pageStartFrom) {
        this.itemPerPage = itemPerPage;
        this.pagePadding = layoutManager.getSpanCount();
        this.pageOffset = pageStartFrom;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        if (lastVisibleItemPosition >= totalItemCount - layoutManager.getSpanCount() - 1) {
            int pageToBeLoaded = (totalItemCount / itemPerPage) + pageOffset;
            if (lastLoadedPage < pageToBeLoaded) {
                scrollListener.requestNewPage(pageToBeLoaded);
                lastLoadedPage = pageToBeLoaded;
            }
        }

        int findFirstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int currentPage = ((findFirstCompletelyVisibleItemPosition + pagePadding) / itemPerPage) + pageOffset;
        if (currentlyDisplayedPage != currentPage) {
            currentlyDisplayedPage = currentPage;
            scrollListener.currentlyOnPage(currentPage);
        }
    }

    /**
     * Get last loaded page
     *
     * @return last loaded page
     */
    public int getLastLoadedPage() {
        return lastLoadedPage;
    }
}
