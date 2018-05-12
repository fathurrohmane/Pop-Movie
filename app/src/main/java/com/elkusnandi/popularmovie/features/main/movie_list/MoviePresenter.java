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
                    if (throwable != null) {                                                            // On error
                        if (throwable instanceof HttpException) {
                            HttpException e = (HttpException) throwable;
                            switch (e.code()) {
                                case 401:
                                    view.setState(BaseView.State.NO_CONNECTION);
                                    return;
                            }
                        }
                        view.changeRecyclerViewItemState(RecyclerViewItemInfoState.reload);
                    } else {
                        if (movieResult.getPage() <= 1) {                                               // On First Data Loaded
                            if (movieResult.getResults() == null || movieResult.getResults().size() == 0) {                                     // No data
                                view.setState(BaseView.State.NO_DATA);
                            } else {                                                                        // Data available
                                view.onDataLoaded(movieResult);
                                view.setState(BaseView.State.SHOW_DATA);
                            }
                        } else if (movieResult.getPage() > 1) {                                         // On More Data Loaded
                            view.onDataLoaded(movieResult);
                        }
                        if (movieResult.getPage() == movieResult.getTotalPages()) {                 // On Reach last page
                            view.changeRecyclerViewItemState(RecyclerViewItemInfoState.bottom_of_page);
                        }
                    }
                });
    }
}
