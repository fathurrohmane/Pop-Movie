package com.elkusnandi.popularmovie.common.base;

import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Taruna 98 on 12/12/2017.
 */

public class BasePresenter {

    protected CompositeDisposable disposable;
    protected Repository repository;
    protected BaseSchedulerProvider schedulerProvider;

    public BasePresenter(CompositeDisposable disposable, Repository repository, BaseSchedulerProvider schedulerProvider) {
        this.disposable = new CompositeDisposable();
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }
}
