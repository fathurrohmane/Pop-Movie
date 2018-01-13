package com.elkusnandi.popularmovie.adapter;

/**
 * Created by Taruna 98 on 13/01/2018.
 */

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elkusnandi.popularmovie.data.model.Tv;
import com.elkusnandi.popularmovie.features.main.tv_list.TvListFragment;

/**
 * Fragment pager adapter for discover menu
 * Created by Taruna 98 on 03/01/2018.
 */

public class DiscoverTvAdapter extends FragmentPagerAdapter {

    public DiscoverTvAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TvListFragment.newInstance(Tv.TV_TYPE_ON_THE_AIR);
            case 1:
                return TvListFragment.newInstance(Tv.TV_TYPE_TODAY_AIRING);
            case 2:
                return TvListFragment.newInstance(Tv.TV_TYPE_POPULAR_TV);
            case 3:
                return TvListFragment.newInstance(Tv.TV_TYPE_RECENTLY_ADDED);
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
                return "On The Air";
            case 1:
                return "Today";
            case 2:
                return "Popular";
            case 3:
                return "Recently Added";
        }
        return super.getPageTitle(position);
    }
}
