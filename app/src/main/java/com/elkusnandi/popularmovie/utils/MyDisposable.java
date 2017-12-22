package com.elkusnandi.popularmovie.utils;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Taruna 98 on 14/12/2017.
 */

public class MyDisposable {

    private static CompositeDisposable INSTANCE;

    private MyDisposable() {
    }

    public static CompositeDisposable getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CompositeDisposable();
        }

        return INSTANCE;
    }
}
