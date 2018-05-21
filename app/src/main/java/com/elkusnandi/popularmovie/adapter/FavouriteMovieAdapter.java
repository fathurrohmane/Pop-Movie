package com.elkusnandi.popularmovie.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.main.movie_list.MovieListFragment;
import com.elkusnandi.popularmovie.features.main.tv_list.TvListFragment;

/**
 * Created by Taruna 98 on 03/01/2018.
 */

public class FavouriteMovieAdapter extends FragmentPagerAdapter {

    private Context context;

    public FavouriteMovieAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MovieListFragment.newInstance(Repository.MOVIE_TYPE_FAVOURITE);
            case 1:
                return TvListFragment.newInstance(Repository.MOVIE_TYPE_FAVOURITE);
            default:
                throw new IllegalArgumentException("Illegal fragment number");
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.tab_layout_movie_title);
            case 1:
                return context.getString(R.string.tab_layout_tv_title);
        }
        return super.getPageTitle(position);
    }
}
