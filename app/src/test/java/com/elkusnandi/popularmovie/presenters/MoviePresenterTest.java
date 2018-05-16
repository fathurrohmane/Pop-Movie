package com.elkusnandi.popularmovie.presenters;

import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.common.interfaces.RecyclerViewItemInfoState;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.data.model.ShowRespond;
import com.elkusnandi.popularmovie.data.provider.AppDatabase;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.main.movie_list.MovieListContract;
import com.elkusnandi.popularmovie.features.main.movie_list.MoviePresenter;
import com.elkusnandi.popularmovie.utils.TestSchedulerProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Test class for MoviePresenter
 * Created by Taruna 98 on 12/12/2017.
 */
@RunWith(JUnit4.class)
public class MoviePresenterTest {

    private MoviePresenter presenter;
    private MovieListContract.View view;
    private List<Movie> movies = new ArrayList<>();

    @Before
    public void setup() {
        presenter = new MoviePresenter(
                new CompositeDisposable(),
                Repository.getInstance(null, TestSchedulerProvider.getInstance()),
                TestSchedulerProvider.getInstance()
        );

        view = new MovieListContract.View() {

            int numberOfPageLoaded;

            @Override
            public void onDataLoaded(ShowRespond<Movie> showRespond) {
                movies.addAll(showRespond.getResults());
                Assert.assertNotNull(showRespond);
                numberOfPageLoaded = showRespond.getPage();
            }

            @Override
            public void changeRecyclerViewItemState(RecyclerViewItemInfoState infoState) {

            }

            @Override
            public int numberOfItem() {
                return numberOfPageLoaded;
            }

            @Override
            public void showProgress() {

            }

            @Override
            public void hideProgress() {

            }

            @Override
            public void setState(State state) {

            }
        };
        presenter.onAttach(view);
    }

    @Test
    public void noDuplicateMovieTest() {
        presenter.loadMovies(Repository.MOVIE_TYPE_NOW_PLAYING, 1, "US");
        presenter.loadMovies(Repository.MOVIE_TYPE_NOW_PLAYING, 2, "US");
        presenter.loadMovies(Repository.MOVIE_TYPE_NOW_PLAYING, 3, "US");
        presenter.loadMovies(Repository.MOVIE_TYPE_NOW_PLAYING, 4, "US");
        presenter.loadMovies(Repository.MOVIE_TYPE_NOW_PLAYING, 5, "US");

        // check for double
        for (Movie movie : movies) {
            int counter = 0;
            for (Movie movie1 : movies) {
                if (movie.getId() == movie1.getId()) {
                    counter++;
                }
            }
            Assert.assertEquals(1, counter);
        }
    }

    @After
    public void done() {
        presenter.onDetach();
    }
}
