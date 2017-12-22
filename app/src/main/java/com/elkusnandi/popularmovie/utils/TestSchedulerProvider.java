package com.elkusnandi.popularmovie.utils;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Taruna 98 on 12/12/2017.
 */

public class TestSchedulerProvider implements BaseSchedulerProvider {

    private static TestSchedulerProvider INSTANCE;

    private TestSchedulerProvider() {
    }

    public static TestSchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestSchedulerProvider();
        }

        return INSTANCE;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
