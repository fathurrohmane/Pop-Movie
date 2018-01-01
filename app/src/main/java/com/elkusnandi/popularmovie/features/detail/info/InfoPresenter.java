package com.elkusnandi.popularmovie.features.detail.info;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Taruna 98 on 19/12/2017.
 */

public class InfoPresenter extends BasePresenter implements InfoContract.Presenter {

    private InfoContract.View view;

    public InfoPresenter(CompositeDisposable disposable
            , Repository repository
            , BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void onAttach(InfoContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        disposable.clear();
    }

    @Override
    public void loadInfo(long movieId) {
        view.showProgress();
        disposable.add(
                repository.getMovieDetails(movieId)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe((movieDetail, throwable) -> {
                            view.infoLoaded(movieDetail);
                            if (view.isDataReady()) {
                                view.hideProgress();
                            }
                        })
        );
    }

    @Override
    public void loadCast(long movieId) {
        disposable.add(
                repository.getMovieCasts(movieId)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe((movieCasts, throwable) -> {
                            view.castLoaded(movieCasts);
                            if (view.isDataReady()) {
                                view.hideProgress();
                            }
                        })
        );
    }
}
