package com.elkusnandi.popularmovie.features.detail.video_list;

import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Video List Presenter
 * Created by Taruna 98 on 19/12/2017.
 */

public class VideoListPresenter extends BasePresenter<VideoListContract.View> implements VideoListContract.Presenter {

    public VideoListPresenter(CompositeDisposable disposable
            , Repository repository
            , BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void loadVideo(long id) {
        view.showProgress();
        disposable.add(
                repository.getMovieVideos(id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe((video, throwable) -> {
                            if (throwable == null) {
                                if (video.getVideoResults() == null || video.getVideoResults().size() == 0) {
                                    view.hideProgress();
                                    view.showNoData();
                                } else {
                                    view.onVideoLoaded(video);
                                    view.hideProgress();
                                }
                            } else {
                                view.hideProgress();
                                view.showError();
                            }
                        })
        );
    }
}
