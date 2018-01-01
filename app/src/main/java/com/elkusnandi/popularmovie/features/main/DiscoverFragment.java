package com.elkusnandi.popularmovie.features.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.features.main.movie_list.MovieListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DiscoverFragment extends Fragment {

    private static final String ARG_PARAM1 = "discover_type";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private String discoverType;
    private String mParam2;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private OnFragmentInteractionListener mListener;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            discoverType = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);

        if (getActivity() != null) {
            // Setup view pager and tab layout
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            sectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setAdapter(sectionsPagerAdapter);
        }

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
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
}
