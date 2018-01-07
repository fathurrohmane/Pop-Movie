package com.elkusnandi.popularmovie.features.detail.video_list;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.VideoAdapter;
import com.elkusnandi.popularmovie.common.base.BaseFragment;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.data.model.Video;
import com.elkusnandi.popularmovie.data.model.VideoResult;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.ui.widget.InformationView;
import com.elkusnandi.popularmovie.utils.AndroidSchedulerProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class VideoListFragment extends BaseFragment implements
        VideoAdapter.OnItemClickListener,
        View.OnClickListener,
        VideoListContract.View {
    private static final String ARG_PARAM1 = "param1";

    @BindView(R.id.rv_video)
    RecyclerView recyclerView;
    @BindView(R.id.view_info)
    InformationView informationView;
    @BindView(R.id.progressbar)
    ContentLoadingProgressBar progressBar;

    private VideoAdapter adapter;
    private Movie movie;
    private VideoListPresenter presenter;

    public VideoListFragment() {
    }

    public static VideoListFragment newInstance(Movie movie) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(ARG_PARAM1);
        }

        presenter = new VideoListPresenter(new CompositeDisposable()
                , Repository.getInstance(AndroidSchedulerProvider.getInstance())
                , AndroidSchedulerProvider.getInstance());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        ButterKnife.bind(this, view);

        adapter = new VideoAdapter(getContext());
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false));

        informationView.addButtonListener(this);

        presenter.onAttach(this);
        presenter.loadVideo(movie.getId());

        return view;
    }

    @Override
    public void onItemClick(VideoResult video) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getKey())));
    }

    @Override
    public void showProgress() {
        progressBar.show();
    }

    @Override
    public void hideProgress() {
        progressBar.hide();
    }

    @Override
    public void onVideoLoaded(Video video) {
        adapter.setData(video.getVideoResults());
    }

    @Override
    public void showError() {
        informationView.showNoConnection();
    }

    @Override
    public void showNoData() {
        informationView.showNoData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_action:
                presenter.loadVideo(movie.getId());
                break;
        }
    }
}
