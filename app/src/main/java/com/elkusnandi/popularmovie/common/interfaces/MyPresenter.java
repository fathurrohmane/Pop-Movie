package com.elkusnandi.popularmovie.common.interfaces;

/**
 * Created by Taruna 98 on 12/12/2017.
 */

public interface MyPresenter<T> {

    void onAttach(T view);

    void onDetach();
}
