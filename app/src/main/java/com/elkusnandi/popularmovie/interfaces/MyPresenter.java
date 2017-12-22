package com.elkusnandi.popularmovie.interfaces;

/**
 * Created by Taruna 98 on 12/12/2017.
 */

public interface MyPresenter<T extends BaseView> {

    void onAttach(T view);

    void onDetach();
}
