package com.elkusnandi.popularmovie.features.main.watch_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.WatchListMovieAdapter;
import com.elkusnandi.popularmovie.common.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment class for displaying user watch list
 * Created by Taruna 98 on 03/01/2018.
 */

public class WatchListFragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Unbinder unbinder;

    public WatchListFragment() {
    }


    public static WatchListFragment newInstance() {
        WatchListFragment fragment = new WatchListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_withonly_viewpager, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Set toolbar
        setActivityToolbarTitle(R.string.toolbar_my_moviedb_watch_list_title);

        if (getActivity() != null) {
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            setPagerAdapter(viewPager, tabLayout, new WatchListMovieAdapter(getChildFragmentManager()));
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}