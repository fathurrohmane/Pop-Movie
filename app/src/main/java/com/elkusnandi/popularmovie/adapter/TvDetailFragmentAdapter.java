package com.elkusnandi.popularmovie.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkusnandi.popularmovie.data.model.Tv;
import com.elkusnandi.popularmovie.features.detail.info.InfoFragment;
import com.elkusnandi.popularmovie.features.detail.video_list.VideoListFragment;

/**
 * Created by Taruna 98 on 14/01/2018.
 */

public class TvDetailFragmentAdapter extends FragmentPagerAdapter {

    private Tv tv;

    public TvDetailFragmentAdapter(FragmentManager fm, Tv tv) {
        super(fm);
        this.tv = tv;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // FIXME: 01/02/2018
                return InfoFragment.newInstance(null);
            case 1:
                return VideoListFragment.newInstance(null);
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

