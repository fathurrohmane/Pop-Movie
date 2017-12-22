package com.elkusnandi.popularmovie.features.detail.video_list;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.VideoAdapter;
import com.elkusnandi.popularmovie.data.model.Movies;
import com.elkusnandi.popularmovie.data.model.Video;
import com.elkusnandi.popularmovie.data.model.VideoResult;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.AndroidSchedulerProvider;
import com.elkusnandi.popularmovie.utils.MyDisposable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListFragment extends Fragment implements
        VideoAdapter.OnItemClickListener,
        VideoListContract.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.rv_video)
    RecyclerView recyclerView;

    private VideoAdapter adapter;
    private Movies movie;
    private VideoListPresenter presenter;

    public VideoListFragment() {
    }

    public static VideoListFragment newInstance(Movies movies) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, movies);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(ARG_PARAM1);
        }

        presenter = new VideoListPresenter(MyDisposable.getInstance()
                , Repository.getInstance(AndroidSchedulerProvider.getInstance())
                , AndroidSchedulerProvider.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        ButterKnife.bind(this, view);

        adapter = new VideoAdapter(getContext());
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

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

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onVideoLoaded(Video video) {
        adapter.setData(video.getVideoResults());
    }
}