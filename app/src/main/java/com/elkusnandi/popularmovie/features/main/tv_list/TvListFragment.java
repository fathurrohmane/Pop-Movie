package com.elkusnandi.popularmovie.features.main.tv_list;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.TvAdapter;
import com.elkusnandi.popularmovie.common.base.BaseFragment;
import com.elkusnandi.popularmovie.common.base.BaseListFragment;
import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.common.interfaces.RecyclerViewItemClickListener;
import com.elkusnandi.popularmovie.data.model.ShowRespond;
import com.elkusnandi.popularmovie.data.model.Tv;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.detail.DetailActivity;
import com.elkusnandi.popularmovie.ui.widget.InformationView;
import com.elkusnandi.popularmovie.utils.AndroidSchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TvListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TvListFragment extends BaseListFragment implements
        TvListContract.View,
        RecyclerViewItemClickListener<Tv>,
        View.OnClickListener {

    private static final String ARG_PARAM1 = "discover_type";
    private static final String ARG_MOVIE_SAVED_INSTANCE = "shows";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.view_info)
    InformationView informationView;

    private TvPresenter presenter;
    private TvAdapter adapter;
    private List<Tv> showList;
    private String discoverType;


    public TvListFragment() {

    }

    public static TvListFragment newInstance(String discoverType) {
        TvListFragment fragment = new TvListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, discoverType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            discoverType = getArguments().getString(ARG_PARAM1);
        }

        presenter = new TvPresenter(
                new CompositeDisposable(),
                Repository.getInstance(AndroidSchedulerProvider.getInstance()),
                AndroidSchedulerProvider.getInstance()
        );

        showList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
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
        adapter = new TvAdapter(getContext());
        adapter.addItemClickListener(this);
        recyclerView.setAdapter(adapter);
        presenter.onAttach(this);
// TODO: 05/01/2018 fix fragment called twice bug
        // if it is not configuration change
        if (savedInstanceState == null) {
            // load movie
            loadShows(discoverType, 1);
        } else {
            // load from savedinstance
            showList = savedInstanceState.getParcelableArrayList(ARG_MOVIE_SAVED_INSTANCE);
            adapter.setData(showList);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(ARG_MOVIE_SAVED_INSTANCE, (ArrayList<? extends Parcelable>) showList);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_action:
                loadShows(discoverType, 1);
                break;
        }
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
    public void setState(State state) {

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
    public void onShowLoaded(ShowRespond<Tv> showRespond) {
        if (adapter.getItemCount() == 0) {
            if (showRespond.getResults() != null && showRespond.getResults().size() > 0) {
                // Add new data
                showList.addAll(showRespond.getResults());
                adapter.setData(showRespond.getResults());
            } else {
                // Show no data view
                informationView.showNoData();
            }
        } else {
            if (showRespond.getResults() != null) {
                // Add more data
                showList.addAll(showRespond.getResults());
                adapter.setData(showRespond.getResults());
            } else {
                // Reach bottom of page cant scroll anymore.
            }
        }
    }

    private void loadShows(String discoverType, int page) {
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
                presenter.loadFavouriteShows(accountId, sessionId, page);
                break;
            case Repository.MOVIE_TYPE_WATCH_LIST:
                presenter.loadWatchList(accountId, sessionId, page);
                break;
            default:
                presenter.loadShows(discoverType, page, "ID");
                break;
        }
    }

    @Override
    public void onReloadRecyclerView() {

    }

    @Override
    public void onItemClick(Tv data) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("tv", data);
        //startActivity(intent);
        // TODO: 13/01/2018 handle DetailActivity to show Tv Details
    }
}
