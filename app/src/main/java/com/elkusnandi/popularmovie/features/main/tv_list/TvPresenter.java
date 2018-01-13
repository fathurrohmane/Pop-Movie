package com.elkusnandi.popularmovie.features.main.tv_list;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.model.ShowRespond;
import com.elkusnandi.popularmovie.data.model.Tv;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Tv presenter class
 * Created by Taruna 98 on 13/01/2018.
 */

public class TvPresenter extends BasePresenter implements TvListContract.Presenter {

    private TvListContract.View view;

    public TvPresenter(CompositeDisposable disposable,
                       Repository repository,
                       BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void onAttach(TvListContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
        disposable.clear();
    }

    @Override
    public void loadShows(String discoverType, int page, String region) {
        view.showProgress();
        disposable.add(getShowsDisposable(repository.getTvs(discoverType, page, region)));
    }

    @Override
    public void loadFavouriteShows(long accountId, String sessionId, int page) {
        view.showProgress();
        disposable.add(getShowsDisposable(repository.getUserFavouriteTvs(accountId, sessionId, page)));
    }

    @Override
    public void loadWatchList(long accountId, String sessionId, int page) {
        view.showProgress();
        disposable.add(getShowsDisposable(repository.getUserTvWatchList(accountId, sessionId, page)));
    }

    private Disposable getShowsDisposable(Single<ShowRespond<Tv>> singleTvRespond) {
        return singleTvRespond.subscribeOn(schedulerProvider.io())
                .timeout(15, TimeUnit.SECONDS)
                .observeOn(schedulerProvider.ui())
                .subscribe((movieResult, throwable) -> {
                    if (movieResult != null) {
                        view.onShowLoaded(movieResult);
                        view.hideProgress();
                    } else {
                        if (throwable instanceof HttpException) {
                            HttpException e = (HttpException) throwable;
                            switch (e.code()) {
                                case 401:
                                    view.showError(1);// TODO: 22/12/2017 show error code to information view
                                    return;
                            }
                        }
                        view.hideProgress();
                        view.showError(1);
                    }
                });
    }
}
