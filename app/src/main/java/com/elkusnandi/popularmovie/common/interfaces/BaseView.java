package com.elkusnandi.popularmovie.common.interfaces;

/**
 * Created by Taruna 98 on 12/12/2017.
 */

public interface BaseView {

    enum State {
        NO_DATA,
        NO_CONNECTION,
        SHOW_DATA,
        SHOW_PROGRESS
    }

    void showProgress();

    void hideProgress();

    void setState(State state);

}
