package com.elkusnandi.popularmovie.features.main.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.common.base.BaseFragment;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.main.movie_list.MovieListFragment;

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
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);

        setActivityToolbarTitle(R.string.toolbar_discover_title);

        // Set TabLayout
        if (getActivity() != null) {
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            DiscoverFragment.SectionsPagerAdapter sectionsPagerAdapter =
                    new DiscoverFragment.SectionsPagerAdapter(getChildFragmentManager());
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setAdapter(sectionsPagerAdapter);
            tabLayout.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MovieListFragment.newInstance(Repository.MOVIE_TYPE_NOW_PLAYING);
                case 1:
                    return MovieListFragment.newInstance(Repository.MOVIE_TYPE_UP_COMING);
                case 2:
                    return MovieListFragment.newInstance(Repository.MOVIE_TYPE_POPULAR);
                case 3:
                    return MovieListFragment.newInstance(Repository.MOVIE_TYPE_RECENTLY_ADDED);
                default:
                    throw new IllegalArgumentException("Illegal fragment number");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Now Playing";
                case 1:
                    return "Up Coming";
                case 2:
                    return "Popular";
                case 3:
                    return "Recently Added";
            }
            return super.getPageTitle(position);
        }
    }
}
