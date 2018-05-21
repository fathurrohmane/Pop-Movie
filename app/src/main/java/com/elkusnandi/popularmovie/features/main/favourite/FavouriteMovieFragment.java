package com.elkusnandi.popularmovie.features.main.favourite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.FavouriteMovieAdapter;
import com.elkusnandi.popularmovie.common.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FavouriteMovieFragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Unbinder unbinder;

    public FavouriteMovieFragment() {
    }


    public static FavouriteMovieFragment newInstance() {
        FavouriteMovieFragment fragment = new FavouriteMovieFragment();
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
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            setPagerAdapter(viewPager, tabLayout, new FavouriteMovieAdapter(getChildFragmentManager(), getContext()));
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
