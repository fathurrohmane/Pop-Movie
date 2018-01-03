package com.elkusnandi.popularmovie.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.main.movie_list.MovieListFragment;

/**
 * Created by Taruna 98 on 03/01/2018.
 */

public class WatchListMovieAdapter extends FragmentPagerAdapter {

    public WatchListMovieAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MovieListFragment.newInstance(Repository.MOVIE_TYPE_WATCH_LIST);
            case 1:
                return MovieListFragment.newInstance(Repository.MOVIE_TYPE_WATCH_LIST);
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
                return "Movie";
            case 1:
                return "TVs";
        }
        return super.getPageTitle(position);
    }
}
