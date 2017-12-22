package com.elkusnandi.popularmovie.features.main.movie_list;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.MovieAdapter;
import com.elkusnandi.popularmovie.data.model.MovieResult;
import com.elkusnandi.popularmovie.data.model.Movies;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.detail.DetailActivity;
import com.elkusnandi.popularmovie.ui.widget.InformationView;
import com.elkusnandi.popularmovie.utils.AndroidSchedulerProvider;
import com.elkusnandi.popularmovie.utils.MyDisposable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieListFragment extends Fragment implements
        MovieListContract.View,
        MovieAdapter.MovieItemClickListener,
        View.OnClickListener {

    private static final String ARG_PARAM1 = "discover_type";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.view_info)
    InformationView informationView;

    private MoviePresenter presenter;
    private MovieAdapter adapter;
    private String discoverType;

    public MovieListFragment() {
        // Required empty public constructor
    }

    public static MovieListFragment newInstance(String movieType) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, movieType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            discoverType = getArguments().getString(ARG_PARAM1);
        }

        presenter = new MoviePresenter(
                MyDisposable.getInstance(),
                Repository.getInstance(AndroidSchedulerProvider.getInstance()),
                AndroidSchedulerProvider.getInstance()
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager gridLayoutManager;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(getContext(), 2); // number of column in Recyclerview
        } else {
            gridLayoutManager = new GridLayoutManager(getContext(), 4); // number of column in Recyclerview
        }

        informationView.addButtonListener(this);
        informationView.hide();

        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MovieAdapter(getContext());
        adapter.addItemClickListener(this);
        recyclerView.setAdapter(adapter);
        presenter.onAttach(this);
        presenter.loadMovies(discoverType);

        return view;
    }

    @Override
    public void showProgress() {
        informationView.hide();
        progressBar.show();
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.hide();
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(int type) {
        switch (type) {
            case 0:
                informationView.showNoData();
            case 1:
                informationView.showNoConnection();
                break;
        }
    }

    @Override
    public void loadMovie(MovieResult movieResult) {
        if (adapter.getItemCount() == 0) {
            if (movieResult.getResults() != null && movieResult.getResults().size() > 0) {
                // Add new data
                adapter.setData(movieResult.getResults());
            } else {
                // Show no data view
                informationView.showNoData();
            }
        } else {
            if (movieResult.getResults() != null) {
                // Add more data
                adapter.setData(movieResult.getResults());
            } else {
                // Reach bottom of page cant scroll anymore.
            }
        }
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        //adapter.removeItemClickListener();
        super.onDestroy();
    }

    @Override
    public void onClick(Movies movie, View view) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_action:
                presenter.loadMovies(discoverType);
                break;
        }
    }
}
