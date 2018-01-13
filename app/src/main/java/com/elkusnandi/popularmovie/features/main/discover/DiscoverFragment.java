package com.elkusnandi.popularmovie.features.main.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.DiscoverMovieAdapter;
import com.elkusnandi.popularmovie.adapter.DiscoverTvAdapter;
import com.elkusnandi.popularmovie.common.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DiscoverFragment extends BaseFragment {

    public static final String ARG_TYPE = "type";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private String type;

    public DiscoverFragment() {
    }

    public static DiscoverFragment newInstance(String type) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            type = getArguments().getString(ARG_TYPE);
        }

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
            setPagerAdapter(viewPager, tabLayout, getPagerAdapter(type));
        }

        return view;
    }

    public FragmentPagerAdapter getPagerAdapter(String type) {
        switch (type) {
            case "movie":
                return new DiscoverMovieAdapter(getChildFragmentManager());
            case "tv":
                return new DiscoverTvAdapter(getChildFragmentManager());
            default:
                return null;
        }
    }

}
