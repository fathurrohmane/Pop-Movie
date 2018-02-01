package com.elkusnandi.popularmovie.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.features.detail.info.InfoFragment;
import com.elkusnandi.popularmovie.features.detail.video_list.VideoListFragment;

/**
 * FragmentAdapter that handle Fragments in MovieDetailActivity
 * <p>
 * Created by Taruna 98 on 14/01/2018.
 */

public class MovieDetailFragmentAdapter extends FragmentPagerAdapter {

    private Movie movie;

    public MovieDetailFragmentAdapter(FragmentManager fm, Movie movie) {
        super(fm);
        this.movie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InfoFragment.newInstance(movie);
            case 1:
                return VideoListFragment.newInstance(movie);
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
                return "Info";
            case 1:
                return "Videos";
            case 2:
                return "Reviews";
            case 3:
                return "Wallpapers";
        }
        return super.getPageTitle(position);
    }
}

