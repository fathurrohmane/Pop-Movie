package com.elkusnandi.popularmovie.features.detail.video_list;

import com.elkusnandi.popularmovie.data.model.Video;

/**
 * Video list contract
 * Created by Taruna 98 on 19/12/2017.
 */

public interface VideoListContract {

    interface View {
        void showProgress();

        void hideProgress();

        void onVideoLoaded(Video video);

        void showError();

        void showNoData();
    }

    interface Presenter{
        void loadVideo(long id);
    }
}
