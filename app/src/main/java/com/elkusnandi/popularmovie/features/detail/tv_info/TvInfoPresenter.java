package com.elkusnandi.popularmovie.features.detail.tv_info;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Presenter implementation for Tv Info Fragment
 * Created by Taruna 98 on 2/19/2018.
 */

public class TvInfoPresenter extends BasePresenter<TvInfoContract.View> implements TvInfoContract.Presenter {

    public TvInfoPresenter(CompositeDisposable disposable
            , Repository repository
            , BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void loadInfo(long tvId) {

    }
}
