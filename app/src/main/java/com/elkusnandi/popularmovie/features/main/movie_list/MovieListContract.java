package com.elkusnandi.popularmovie.features.main.movie_list;

import com.elkusnandi.popularmovie.data.model.MovieResult;
import com.elkusnandi.popularmovie.interfaces.BaseView;
import com.elkusnandi.popularmovie.interfaces.MyPresenter;

/**
 * Created by Taruna 98 on 14/12/2017.
 */

public interface MovieListContract {

    interface View extends BaseView {

        void showProgress();

        void hideProgress();

        void showError(int type);

        void loadMovie(MovieResult movieResult);

    }

    interface Presenter extends MyPresenter<View> {

        void loadMovies(String movieType);

    }
}
