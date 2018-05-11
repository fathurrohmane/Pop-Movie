package com.elkusnandi.popularmovie.features.detail.info;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.model.PostMovie;
import com.elkusnandi.popularmovie.data.provider.AppDatabase;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

/**
 * Created by Taruna 98 on 19/12/2017.
 */

public class InfoPresenter extends BasePresenter<InfoContract.View> implements InfoContract.Presenter {

    public InfoPresenter(CompositeDisposable disposable
            , Repository repository
            , BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
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

    @Override
    public void addToFavourite(long accountId, String sessionId, PostMovie favouriteMovie) {
        disposable.add(
                repository.addMovieToFavourite(accountId, sessionId, favouriteMovie)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe((respond, throwable) -> {
                            if (throwable == null) {
                                view.showRespond(respond);
                            } else {
                                if (throwable instanceof HttpException) {
                                    HttpException httpException = (HttpException) throwable;
                                    view.showToast(httpException.getMessage() + " Error code :" + httpException.code());
                                }
                                view.showToast(throwable.getMessage());
                            }
                        })
        );
    }

    @Override
    public void addToWatchList(long accountId, String sessionId, PostMovie favouriteMovie) {
        disposable.add(
                repository.addMovieToWatchList(accountId, sessionId, favouriteMovie)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe((respond, throwable) -> {
                            if (throwable == null) {
                                view.showRespond(respond);
                            } else {
                                if (throwable instanceof HttpException) {
                                    HttpException httpException = (HttpException) throwable;
                                    view.showToast(httpException.getMessage() + " Error code :" + httpException.code());
                                }
                                view.showToast(throwable.getMessage());
                            }
                        })
        );
    }

    @Override
    public void checkFavourite(int movieId) {
        disposable.add(
                repository.isMovieFavourite(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe((aBoolean, throwable) -> {
                    if (throwable == null) {
                        view.setFavouriteMovieDrawableButton(aBoolean);
                    } else {
                        if (throwable instanceof HttpException) {
                            HttpException httpException = (HttpException) throwable;
                            view.showToast(httpException.getMessage() + " Error code :" + httpException.code());
                        }
                        view.showToast(throwable.getMessage());
                    }
                })
        );
    }

    public void removeFavourite(int movieId) {
        disposable.add(
                repository.removeMovieFromFavourite(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe((integer, throwable) ->
                    view.showToast("Movie removed")
                )
        );
    }

    public void addToFavourite(int movieId) {
        disposable.add(
                repository.addMovieToFavourite(movieId)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe((aLong, throwable) ->
                            view.showToast("Movie favourite")
                        )
        );
    }
}
