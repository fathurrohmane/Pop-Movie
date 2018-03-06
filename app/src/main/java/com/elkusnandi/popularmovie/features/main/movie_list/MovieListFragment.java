package com.elkusnandi.popularmovie.features.main.movie_list;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.MovieAdapter;
import com.elkusnandi.popularmovie.common.base.BaseListFragment;
import com.elkusnandi.popularmovie.common.interfaces.RecyclerViewItemClickListener;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.data.model.ShowRespond;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.detail.DetailActivity;
import com.elkusnandi.popularmovie.utils.AndroidSchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieListFragment extends BaseListFragment implements
        MovieListContract.View,
        RecyclerViewItemClickListener<Movie>,
        View.OnClickListener {

    private static final String ARG_PARAM1 = "discover_type";
    private static final String ARG_MOVIE_SAVED_INSTANCE = "movies";

    private MoviePresenter presenter;
    private MovieAdapter adapter;
    private List<Movie> movieList;
    private String discoverType;

    public MovieListFragment() {
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
                new CompositeDisposable(),
                Repository.getInstance(AndroidSchedulerProvider.getInstance()),
                AndroidSchedulerProvider.getInstance()
        );

        movieList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;
        ButterKnife.bind(this, view);

        informationView.addButtonListener(this);
        adapter = new MovieAdapter(getContext());
        adapter.addItemClickListener(this);
        recyclerView.setAdapter(adapter);
        presenter.onAttach(this);
// TODO: 05/01/2018 fix fragment called twice bug
        // if it is not configuration change
        if (savedInstanceState == null) {
            // load movie
            loadMovies(discoverType, 1);
        } else {
            // load from saved instance
            if (savedInstanceState.containsKey(ARG_MOVIE_SAVED_INSTANCE)) {
                movieList = savedInstanceState.getParcelableArrayList(ARG_MOVIE_SAVED_INSTANCE);
                adapter.setData(movieList);
                setState(State.SHOW_DATA);
            } else {
                setState(State.NO_DATA);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (movieList.size() > 0) {
            outState.putParcelableArrayList(ARG_MOVIE_SAVED_INSTANCE, (ArrayList<? extends Parcelable>) movieList);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMovieLoaded(ShowRespond<Movie> showRespond) {
        if (adapter.getItemCount() == 0) {
            if (showRespond.getResults() != null && showRespond.getResults().size() > 0) {
                // Add new data
                movieList.addAll(showRespond.getResults());
                adapter.setData(showRespond.getResults());
                setState(State.SHOW_DATA);
            } else {
                // Show no data view
                setState(State.NO_DATA);
            }
        } else {
            if (showRespond.getResults() != null) {
                // Add more data
                movieList.addAll(showRespond.getResults());
                adapter.setData(showRespond.getResults());
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
    public void onItemClick(Movie movie, View view) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_action:
                loadMovies(discoverType, 1);
                break;
        }
    }

    private void loadMovies(String discoverType, int page) {
        SharedPreferences sharedPreferences;
        long accountId;
        String sessionId;

        if (getContext() != null) {
            sharedPreferences = getContext().getSharedPreferences(getString(R.string.sharedpreference_id), Context.MODE_PRIVATE);
            accountId = sharedPreferences.getLong(getString(R.string.sharedpreference_account_id), -1L);
            sessionId = sharedPreferences.getString(getString(R.string.sharedpreference_session_id), "");
        } else {
            throw new IllegalArgumentException("Missing Context");
        }

        switch (discoverType) {
            case Repository.MOVIE_TYPE_FAVOURITE:
                presenter.loadFavouriteMovies(accountId, sessionId, page);
                break;
            case Repository.MOVIE_TYPE_WATCH_LIST:
                presenter.loadWatchList(accountId, sessionId, page);
                break;
            default:
                presenter.loadMovies(discoverType, page, "ID");
                break;
        }
    }
}
