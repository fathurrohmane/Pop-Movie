package com.elkusnandi.popularmovie.common.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.common.interfaces.BaseView;
import com.elkusnandi.popularmovie.ui.widget.InformationView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Base class for fragment that only show recyclerview
 * Created by Taruna 98 on 3/5/2018.
 */

public class BaseListFragment extends BaseFragment implements BaseView {

    // default span count
    private int spanCountPortrait = 3;
    private int spanCountLandscape = 5;

    @BindView(R.id.recyclerview)
    protected RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    protected ContentLoadingProgressBar progressBar;
    @BindView(R.id.view_info)
    protected InformationView informationView;

    protected GridLayoutManager gridLayoutManager;

    public BaseListFragment() {

    }

    /**
     * Set span size for recyclerview
     * Call it before onCreateView
     * @param spanCountPortrait
     * @param spanCountLandscape
     */
    public void setSpanCountRecyclerView(int spanCountPortrait, int spanCountLandscape) {
        this.spanCountPortrait = spanCountPortrait;
        this.spanCountLandscape = spanCountLandscape;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, view);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(getContext(), spanCountPortrait); // number of column in Recyclerview
        } else {
            gridLayoutManager = new GridLayoutManager(getContext(), spanCountLandscape); // number of column in Recyclerview
        }

        informationView.hide();
        recyclerView.setLayoutManager(gridLayoutManager);
        return view;
    }

    @Override
    public void showProgress() {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.show();
        informationView.hide();
    }

    @Override
    public void hideProgress() {
        progressBar.hide();
        recyclerView.setVisibility(View.VISIBLE);
        informationView.hide();
    }

    @Override
    public void setState(State state) {
        switch (state) {
            case SHOW_PROGRESS:
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.show();
                informationView.hide();
                break;
            case NO_CONNECTION:
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.hide();
                informationView.showNoConnection();
                break;
            case SHOW_DATA:
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.hide();
                informationView.hide();
                break;
            case NO_DATA:
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.hide();
                informationView.showNoData();
                break;
        }
    }

    /**
     * Class to handle recyclerview span size depends on orientation and position
     *
     */
    public class SpanSizeLookUp extends GridLayoutManager.SpanSizeLookup {

        private BaseRecyclerViewAdapter adapter;

        public SpanSizeLookUp(BaseRecyclerViewAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getSpanSize(int position) {
            if (adapter.getItemCount() - 1 == position) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    return spanCountPortrait;
                } else {
                    return spanCountLandscape;
                }
            }
            return 1;
        }
    }
}
