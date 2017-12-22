package com.elkusnandi.popularmovie.utils;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Taruna 98 on 12/12/2017.
 */

public class AndroidSchedulerProvider implements BaseSchedulerProvider {

    private static AndroidSchedulerProvider INSTANCE;

    public static synchronized AndroidSchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AndroidSchedulerProvider();
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
