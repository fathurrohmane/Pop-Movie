package com.elkusnandi.popularmovie.features.main.movie_list;

import com.elkusnandi.popularmovie.data.model.MovieRespond;
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

        void loadMovie(MovieRespond movieRespond);

    }

    interface Presenter extends MyPresenter<View> {

        void loadMovies(String discoverType, int page, String region);

        void loadFavouriteMovies(long accountId, String sessionId, int page);

    }
}
