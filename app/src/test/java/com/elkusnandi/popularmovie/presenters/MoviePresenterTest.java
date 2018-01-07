package com.elkusnandi.popularmovie.presenters;

import com.elkusnandi.popularmovie.data.model.ShowRespond;
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

import io.reactivex.disposables.CompositeDisposable;

/**
 * Test class for MoviePresenter
 * Created by Taruna 98 on 12/12/2017.
 */
@RunWith(JUnit4.class)
public class MoviePresenterTest {

    private MoviePresenter presenter;
    private MovieListContract.View view;

    @Before
    public void setup() {
        presenter = new MoviePresenter(
                new CompositeDisposable(),
                Repository.getInstance(TestSchedulerProvider.getInstance()),
                TestSchedulerProvider.getInstance()
        );

        view = new MovieListContract.View() {

            @Override
            public void showProgress() {

            }

            @Override
            public void hideProgress() {

            }

            @Override
            public void showError(int type) {

            }

            @Override
            public void onMovieLoaded(ShowRespond showRespond) {
                Assert.assertNotNull(showRespond);
                Assert.assertEquals(1, showRespond.getPage());
            }

        };
    }

    @Test
    public void loadMovieTest() {
        presenter.onAttach(view);
        presenter.loadMovies("movie", 1, "ID");
    }

    @After
    public void done() {
        presenter.onDetach();
    }
}
