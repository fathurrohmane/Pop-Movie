package com.elkusnandi.popularmovie.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.main.movie_list.MovieListFragment;

/**
 * Fragment pager adapter for discover menu
 * Created by Taruna 98 on 03/01/2018.
 */

public class DiscoverMovieAdapter extends FragmentPagerAdapter {

    public DiscoverMovieAdapter(FragmentManager fm) {
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
