package com.elkusnandi.popularmovie.common.base;

import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.elkusnandi.popularmovie.R;

/**
 * Base Fragment
 * <p>
 * Created by Taruna 98 on 03/01/2018.
 */

public class BaseFragment extends Fragment {

    /**
     * Set Activity toolbar title and subtitle from a Fragment
     *
     * @param title title of the Toolbar
     */
    protected void setActivityToolbarTitle(@StringRes int title) {
        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setTitle(getString(title));
            } else {
                throw new IllegalArgumentException("Toolbar is null. This Activity doesnt have toolbar");
            }
        } else {
            throw new IllegalArgumentException("Activity is null. This fragment isnt attached to any Activity");
        }
    }

    /**
     * Set Activity toolbar title and subtitle from a Fragment
     *
     * @param title    title of the Toolbar
     * @param subTitle subtitle of the Toolbar
     */
    protected void setActivityToolbarTitle(@StringRes int title, @StringRes int subTitle) {
        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setTitle(getString(title));
                toolbar.setSubtitle(getString(subTitle));
            } else {
                throw new IllegalArgumentException("Toolbar is null. This Activity doesnt have toolbar");
            }
        } else {
            throw new IllegalArgumentException("Activity is null. This fragment isnt attached to any Activity");
        }
    }

    /**
     * Set adapter for view pager from the fragment itself
     *
     * @param viewPager            viewpager
     * @param tabLayout            tablayout
     * @param fragmentPagerAdapter fragment pager adapter
     */
    protected void setPagerAdapter(ViewPager viewPager, TabLayout tabLayout, FragmentPagerAdapter fragmentPagerAdapter) {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setVisibility(View.VISIBLE);
    }
}
