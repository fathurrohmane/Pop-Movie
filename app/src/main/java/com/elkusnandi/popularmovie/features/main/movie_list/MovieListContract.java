package com.elkusnandi.popularmovie.features.main.movie_list;

import com.elkusnandi.popularmovie.common.base.BaseRecyclerViewAdapter;
import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.common.interfaces.RecyclerViewItemInfoState;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.data.model.ShowRespond;

/**
 * Movie list contract for Movie Presenter and View
 * Created by Taruna 98 on 14/12/2017.
 */

public interface MovieListContract {

    interface View extends BaseView {

        void onDataFirstLoaded(ShowRespond<Movie> showRespond);

        void onDataContinueLoaded(ShowRespond<Movie> showRespond);

        void changeRecyclerViewItemState(RecyclerViewItemInfoState infoState);

        int numberOfItem();

    }

    interface Presenter {

        void loadMovies(String discoverType, int page, String region);

        void loadFavouriteMovies(long accountId, String sessionId, int page);

        void loadWatchList(long accountId, String sessionId, int page);

    }
}
