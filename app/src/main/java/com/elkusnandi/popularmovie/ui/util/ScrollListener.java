package com.elkusnandi.popularmovie.ui.util;

/**
 * Created by Taruna 98 on 3/8/2018.
 */

public interface ScrollListener {

    /**
     * Send int which page the recyclerview currenly is on
     *
     * @param page number of page. start from 0
     */
    void currentlyOnPage(int page);

    /**
     * Called when new page is being requested
     *
     * @param newPage the number of new page
     */
    void requestNewPage(int newPage);

}
