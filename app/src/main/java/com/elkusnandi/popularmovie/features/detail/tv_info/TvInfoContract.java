package com.elkusnandi.popularmovie.features.detail.tv_info;

import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.common.interfaces.MyPresenter;
import com.elkusnandi.popularmovie.data.model.Respond;
import com.elkusnandi.popularmovie.data.model.TvDetail;

/**
 * Contract class for Tv Info Fragment
 * Created by Taruna 98 on 2/19/2018.
 */

public interface TvInfoContract {

    interface View extends BaseView {
        void showProgress();

        void hideProgress();

        void infoLoaded(TvDetail tvDetail);

        boolean isDataReady();

        void showRespond(Respond respond);

        void showToast(String message);
    }

    interface Presenter extends MyPresenter<View> {
        void loadInfo(long tvId);
    }
}
