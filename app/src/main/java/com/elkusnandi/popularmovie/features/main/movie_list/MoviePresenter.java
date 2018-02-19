package com.elkusnandi.popularmovie.features.main.movie_list;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.data.model.ShowRespond;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Movie Presenter implementation
 * Created by Taruna 98 on 12/12/2017.
 */

public class MoviePresenter extends BasePresenter< MovieListContract.View> implements MovieListContract.Presenter {

    public MoviePresenter(CompositeDisposable disposable,
                          Repository repository,
                          BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void loadMovies(String discoverType, int page, String region) {
        view.showProgress();
        disposable.add(getMoviesDisposable(repository.getMovies(discoverType, page, region)));
    }

    @Override
    public void loadFavouriteMovies(long accountId, String sessionId, int page) {
        view.showProgress();
        disposable.add(getMoviesDisposable(repository.getUserFavouriteMovies(accountId, sessionId, page)));
    }

    @Override
    public void loadWatchList(long accountId, String sessionId, int page) {
        view.showProgress();
        disposable.add(getMoviesDisposable(repository.getUserMovieWatchList(accountId, sessionId, page)));
    }

    private Disposable getMoviesDisposable(Single<ShowRespond<Movie>> movieRespondSingle) {
        return movieRespondSingle.subscribeOn(schedulerProvider.io())
                .timeout(15, TimeUnit.SECONDS)
                .observeOn(schedulerProvider.ui())
                .subscribe((movieResult, throwable) -> {
                    if (movieResult != null) {
                        view.onMovieLoaded(movieResult);
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
