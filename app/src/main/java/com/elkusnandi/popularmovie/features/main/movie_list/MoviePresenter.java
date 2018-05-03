package com.elkusnandi.popularmovie.features.main.movie_list;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.common.interfaces.RecyclerViewItemClickListener;
import com.elkusnandi.popularmovie.common.interfaces.RecyclerViewItemInfoState;
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

public class MoviePresenter extends BasePresenter<MovieListContract.View> implements MovieListContract.Presenter {

    public MoviePresenter(CompositeDisposable disposable,
                          Repository repository,
                          BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void loadMovies(String discoverType, int page, String region) {
        disposable.add(getMoviesDisposable(repository.getMovies(discoverType, page, region)));
    }

    @Override
    public void loadFavouriteMovies(long accountId, String sessionId, int page) {
        disposable.add(getMoviesDisposable(repository.getUserFavouriteMovies(accountId, sessionId, page)));
    }

    @Override
    public void loadWatchList(long accountId, String sessionId, int page) {
        disposable.add(getMoviesDisposable(repository.getUserMovieWatchList(accountId, sessionId, page)));
    }

    private Disposable getMoviesDisposable(Single<ShowRespond<Movie>> movieRespondSingle) {
        return movieRespondSingle.subscribeOn(schedulerProvider.io())
                .timeout(15, TimeUnit.SECONDS)
                .observeOn(schedulerProvider.ui())
                .subscribe((movieResult, throwable) -> {
                    if (movieResult != null) {
                        if (view.numberOfItem() == 0) {                         // recycler view is empty
                            if (movieResult.getResults() != null
                                    && movieResult.getResults().size() > 0) {   // Add new data
                                view.onDataFirstLoaded(movieResult);
                                view.setState(BaseView.State.SHOW_DATA);
                            } else {                                            // Show no data view
                                view.setState(BaseView.State.NO_DATA);
                            }
                        } else {
                            if (movieResult.getResults() != null) {             // Add more data
                                view.onDataContinueLoaded(movieResult);
                                //view.changeRecyclerViewItemState(RecyclerViewItemInfoState.loading);
                            } else {                                            // Reach bottom of page cant scroll anymore
                                view.changeRecyclerViewItemState(RecyclerViewItemInfoState.bottom_of_page);
                            }
                        }
                    } else {
                        if (throwable instanceof HttpException) {
                            HttpException e = (HttpException) throwable;
                            switch (e.code()) {
                                case 401:
                                    view.setState(BaseView.State.NO_CONNECTION);// TODO: 22/12/2017 show error code to information view
                                    return;
                            }
                        }
                        view.changeRecyclerViewItemState(RecyclerViewItemInfoState.reload);
                    }
                });
    }
}
