package com.elkusnandi.popularmovie.features.detail.video_list;

import com.elkusnandi.popularmovie.data.model.Video;
import com.elkusnandi.popularmovie.interfaces.BaseView;
import com.elkusnandi.popularmovie.interfaces.MyPresenter;

/**
 * Created by Taruna 98 on 19/12/2017.
 */

public interface VideoListContract {

    interface View extends BaseView {
        void showProgress();

        void hideProgress();

        void onVideoLoaded(Video video);

        void showError();

        void showNoData();
    }

    interface Presenter extends MyPresenter<View> {
        void loadVideo(long id);

    }
}
