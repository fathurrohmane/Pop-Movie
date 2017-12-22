package com.elkusnandi.popularmovie.utils;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by Taruna 98 on 12/12/2017.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

}
