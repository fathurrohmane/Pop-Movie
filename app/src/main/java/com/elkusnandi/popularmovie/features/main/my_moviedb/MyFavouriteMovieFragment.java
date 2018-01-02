package com.elkusnandi.popularmovie.features.main.my_moviedb;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.main.movie_list.MovieListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyFavouriteMovieFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Unbinder unbinder;
    private String mParam1;
    private String mParam2;


    public MyFavouriteMovieFragment() {
        // Required empty public constructor
    }


    public static MyFavouriteMovieFragment newInstance() {
        MyFavouriteMovieFragment fragment = new MyFavouriteMovieFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_favourite_movie, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getActivity() != null) {
            // Set toolbar
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(getString(R.string.toolbar_my_moviedb_favourite_title));

            // Setup view pager and tab layout
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            SectionsPagerAdapter sectionsPagerAdapter =
                    new SectionsPagerAdapter(getChildFragmentManager());
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setAdapter(sectionsPagerAdapter);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
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
