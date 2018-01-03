package com.elkusnandi.popularmovie.features.detail.info;

import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.common.interfaces.MyPresenter;
import com.elkusnandi.popularmovie.data.model.MovieCasts;
import com.elkusnandi.popularmovie.data.model.MovieDetail;

/**
 * Created by Taruna 98 on 19/12/2017.
 */

public interface InfoContract {

    interface View extends BaseView {
        void showProgress();

        void hideProgress();

        void infoLoaded(MovieDetail movieDetail);

        void castLoaded(MovieCasts movieCasts);

        boolean isDataReady();
    }

    interface Presenter extends MyPresenter<View> {
        void loadInfo(long movieId);

        void loadCast(long movieId);
    }

}
