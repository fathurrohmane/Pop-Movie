package com.elkusnandi.popularmovie.features.main.tv_list;

import com.elkusnandi.popularmovie.data.model.ShowRespond;
import com.elkusnandi.popularmovie.data.model.Tv;

/**
 * Created by Taruna 98 on 13/01/2018.
 */

public interface TvListContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showError(int type);

        void onShowLoaded(ShowRespond<Tv> showRespond);

    }

    interface Presenter{

        void loadShows(String discoverType, int page, String region);

        void loadFavouriteShows(long accountId, String sessionId, int page);

        void loadWatchList(long accountId, String sessionId, int page);

    }
}
