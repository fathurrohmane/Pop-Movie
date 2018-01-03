package com.elkusnandi.popularmovie.utils;

import android.support.v7.util.DiffUtil;

import com.elkusnandi.popularmovie.data.model.Movie;

import java.util.List;

/**
 * Created by Taruna 98 on 16/12/2017.
 */

public class MovieResultDiffUtils extends DiffUtil.Callback {

    private List<Movie> oldList;
    private List<Movie> newList;

    public MovieResultDiffUtils(List<Movie> oldList, List<Movie> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }
}
