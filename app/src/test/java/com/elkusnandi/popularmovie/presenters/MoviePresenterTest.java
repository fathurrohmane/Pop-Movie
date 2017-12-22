package com.elkusnandi.popularmovie.presenters;

import com.elkusnandi.popularmovie.data.model.MovieResult;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.main.movie_list.MovieListContract;
import com.elkusnandi.popularmovie.features.main.movie_list.MoviePresenter;
import com.elkusnandi.popularmovie.utils.MyDisposable;
import com.elkusnandi.popularmovie.utils.TestSchedulerProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Taruna 98 on 12/12/2017.
 */
@RunWith(JUnit4.class)
public class MoviePresenterTest {

    MoviePresenter presenter;
    MovieListContract.View view;

    @Before
    public void setup() {
        presenter = new MoviePresenter(
                MyDisposable.getInstance(),
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
            public void loadMovie(MovieResult movieResult) {
                Assert.assertNotNull(movieResult);
                Assert.assertEquals(1, movieResult.getPage());
            }

        };
    }

    @Test
    public void loadMovieTest() {
        presenter.onAttach(view);
        presenter.loadMovies("tv");
    }

    @After
    public void done() {
        presenter.onDetach();
    }
}
