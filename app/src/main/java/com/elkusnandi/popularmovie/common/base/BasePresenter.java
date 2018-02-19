package com.elkusnandi.popularmovie.common.base;

import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.common.interfaces.MyPresenter;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Base presenter class
 * Created by Taruna 98 on 12/12/2017.
 */

public class BasePresenter<V extends BaseView> implements MyPresenter<V>{

    protected V view;
    protected CompositeDisposable disposable;
    protected Repository repository;
    protected BaseSchedulerProvider schedulerProvider;

    public BasePresenter(CompositeDisposable disposable, Repository repository, BaseSchedulerProvider schedulerProvider) {
        this.disposable = disposable;
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        disposable.clear();
    }
}
