package com.elkusnandi.popularmovie.features.detail.tv_info;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Presenter implementation for Tv Info Fragment
 * Created by Taruna 98 on 2/19/2018.
 */

public class TvInfoPresenter extends BasePresenter implements TvInfoContract.Presenter {

    private TvInfoContract.View view;

    public TvInfoPresenter(CompositeDisposable disposable
            , Repository repository
            , BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void onAttach(TvInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        disposable.clear();
    }

    @Override
    public void loadInfo(long tvId) {

    }
}
