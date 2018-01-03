package com.elkusnandi.popularmovie.features.main.my_moviedb;


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
import butterknife.Unbinder;


public class UserFavouriteMovieFragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Unbinder unbinder;

    public UserFavouriteMovieFragment() {
    }


    public static UserFavouriteMovieFragment newInstance() {
        UserFavouriteMovieFragment fragment = new UserFavouriteMovieFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_my_favourite_movie, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Set toolbar
        setActivityToolbarTitle(R.string.toolbar_my_moviedb_favourite_title);

        if (getActivity() != null) {
            // Setup view pager and tab layout
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            SectionsPagerAdapter sectionsPagerAdapter =
                    new SectionsPagerAdapter(getChildFragmentManager());
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setAdapter(sectionsPagerAdapter);
            tabLayout.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MovieListFragment.newInstance(Repository.MOVIE_TYPE_FAVOURITE);
                case 1:
                    return MovieListFragment.newInstance(Repository.MOVIE_TYPE_FAVOURITE);
                default:
                    throw new IllegalArgumentException("Illegal fragment number");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Movies";
                case 1:
                    return "TVs";
            }
            return super.getPageTitle(position);
        }
    }
}
