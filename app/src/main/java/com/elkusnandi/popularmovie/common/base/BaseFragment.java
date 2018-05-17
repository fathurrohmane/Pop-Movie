package com.elkusnandi.popularmovie.common.base;

import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.model.Respond;

import static android.content.Context.MODE_PRIVATE;

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

    protected void showRespond(Respond respond) {
        switch (respond.getStatusCode()) {
            case 1:
                // mew added
            case 12:
                // update data
                Toast.makeText(getContext(), getString(R.string.success_add_data), Toast.LENGTH_LONG).show();
                break;
            case 13:
                // delete data
                Toast.makeText(getContext(), getString(R.string.success_delete_data), Toast.LENGTH_LONG).show();
                break;
            case 3:
                // error auth
                Toast.makeText(getContext(), getString(R.string.error_authentication), Toast.LENGTH_LONG).show();
                if (getContext() != null) {
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.sharedpreference_id), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.sharedpreference_login_status), false);
                    editor.putString(getString(R.string.sharedpreference_session_id), "");
                    editor.putLong(getString(R.string.sharedpreference_account_id), 0L);
                    editor.apply();
                }
                break;
            case 34:
                // resource not found / wrong id
                Toast.makeText(getContext(), getString(R.string.error_resource_not_found), Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getContext(), getString(R.string.error_unknown, respond.getStatusCode()), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
