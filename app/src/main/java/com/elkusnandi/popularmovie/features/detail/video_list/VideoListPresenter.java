package com.elkusnandi.popularmovie.features.detail.video_list;

import com.elkusnandi.popularmovie.BuildConfig;
import com.elkusnandi.popularmovie.common.base.BasePresenter;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Taruna 98 on 19/12/2017.
 */

public class VideoListPresenter extends BasePresenter implements VideoListContract.Presenter {

    private VideoListContract.View view;

    public VideoListPresenter(CompositeDisposable disposable
            , Repository repository
            , BaseSchedulerProvider schedulerProvider) {
        super(disposable, repository, schedulerProvider);
    }

    @Override
    public void onAttach(VideoListContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        disposable.clear();
    }

    @Override
    public void loadVideo(long id) {
        view.showProgress();
        disposable.add(
                repository.getMovieVideos(BuildConfig.MOVIE_DB_API_KEY, id)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe((video, throwable) -> {
                            if (video.getVideoResults() == null) {
                                // Show no video
                            } else {
                                view.onVideoLoaded(video);
                                view.hideProgress();
                            }
                        })
        );
    }
}