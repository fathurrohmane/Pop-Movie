package com.elkusnandi.popularmovie.features.main.movie_list;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.model.MovieRespond;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

/**
 * Created by Taruna 98 on 12/12/2017.
 */

public class MoviePresenter extends BasePresenter implements MovieListContract.Presenter {

    private MovieListContract.View view;

    public MoviePresenter(CompositeDisposable disposable,
                          Repository repository,
                          BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void onAttach(MovieListContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
        disposable.clear();
    }

    public void loadMovies(String discoverType) {
        view.showProgress();
        Single<MovieRespond> single;
        switch (discoverType) {
            case "now_playing":
                single = repository.getNowPlayingMovies(1, "ID");
                break;
            case "up_coming":
                single = repository.getUpComingMovies(1, "ID");
                break;
            case "popular":
                single = repository.getPopularMovies(1, "ID");
                break;
            case "recently_added":
                single = repository.getRecentlyAddedMovies(1, "ID");
                break;
            default:
                throw new IllegalArgumentException("Discover Type Not Found");
        }

        disposable.add(single.subscribeOn(schedulerProvider.io())
                .timeout(15, TimeUnit.SECONDS)
                .observeOn(schedulerProvider.ui())
                .subscribe((movieResult, throwable) -> {
                    if (movieResult != null) {
                        view.loadMovie(movieResult);
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
                })
        );
    }
}
