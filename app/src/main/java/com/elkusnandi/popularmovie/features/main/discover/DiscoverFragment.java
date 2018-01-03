package com.elkusnandi.popularmovie.features.main.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.DiscoverMovieAdapter;
import com.elkusnandi.popularmovie.common.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_withonly_viewpager, container, false);
        ButterKnife.bind(this, view);

        setActivityToolbarTitle(R.string.toolbar_discover_title);

        if (getActivity() != null) {
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            setPagerAdapter(viewPager, tabLayout, new DiscoverMovieAdapter(getChildFragmentManager()));
        }

        return view;
    }

}
