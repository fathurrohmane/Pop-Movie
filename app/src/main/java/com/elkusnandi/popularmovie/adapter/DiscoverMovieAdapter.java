package com.elkusnandi.popularmovie.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.main.movie_list.MovieListFragment;

/**
 * Fragment pager adapter for discover menu
 * Created by Taruna 98 on 03/01/2018.
 */

public class DiscoverMovieAdapter extends FragmentPagerAdapter {

    public static final int NUMBER_OF_TAB = 4;

    private Context context;

    public DiscoverMovieAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
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
        return NUMBER_OF_TAB;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.tab_layout_now_playing_title);
            case 1:
                return context.getString(R.string.tab_layout_upcoming_title);
            case 2:
                return context.getString(R.string.tab_layout_popular_title);
            case 3:
                return context.getString(R.string.tab_layout_recently_added_title);
        }
        return super.getPageTitle(position);
    }
}
