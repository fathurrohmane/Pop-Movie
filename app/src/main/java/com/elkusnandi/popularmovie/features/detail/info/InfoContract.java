package com.elkusnandi.popularmovie.features.detail.info;

import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.data.model.CastRespond;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.PostMovie;
import com.elkusnandi.popularmovie.data.model.Respond;

/**
 * Info contract
 * Created by Taruna 98 on 19/12/2017.
 */

public interface InfoContract {

    interface View extends BaseView {
        void showProgress();

        void hideProgress();

        void infoLoaded(MovieDetail movieDetail);

        void castLoaded(CastRespond castRespond);

        boolean isDataReady();

        void showRespond(Respond respond);

        void showToast(String message);
    }

    interface Presenter {
        void loadInfo(long movieId);

        void loadCast(long movieId);

        void addToFavourite(long accountId, String sessionId, PostMovie favouriteMovie);

        void addToWatchList(long accountId, String sessionId, PostMovie favouriteMovie);
    }

}
