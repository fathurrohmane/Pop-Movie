/*
* Copyright (C) 2017 Fathurrohman Elkusnandi
*/

package com.elkusnandi.popularmovie.adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.model.Movies;
import com.elkusnandi.popularmovie.utils.GlideApp;
import com.elkusnandi.popularmovie.utils.MovieResultDiffUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taruna 98 on 22/06/2017.
 * Movie Adapter for Movie Recyclerview
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Movies> mMovieList;
    private MovieItemClickListener onClickListener;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
        mMovieList = new ArrayList<>();
    }

    public void addItemClickListener(MovieItemClickListener movieItemClickListener) {
        this.onClickListener = movieItemClickListener;

    }

    public void removeItemClickListener() {
        if (onClickListener != null) {
            onClickListener = null;
        }
    }

    /**
     * Set Movie data for recyclerview
     */
    public void setData(List<Movies> movieList) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MovieResultDiffUtils(mMovieList, movieList));

        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        result.dispatchUpdatesTo(this);
//        if (mMovieList.size() == 0) {
//            mMovieList.addAll(movieList);
//            notifyDataSetChanged();
//        } else {
//            int currentSize = mMovieList.size();
//            mMovieList.addAll(movieList);
//            notifyItemInserted(currentSize);
//        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new MovieViewHolder(view);
                break;
            default:
                view = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new MovieViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder viewHolder = (MovieViewHolder) holder;
        String url = mMovieList.get(position).getPosterUrl(Movies.PosterSize.w342);
        GlideApp.with(mContext)
                .load(url)
                .error(R.drawable.ic_image_error)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(viewHolder.mImageViewMovie);
//        Picasso.with(mContext)
//                .load(url)
//                .error(R.drawable.ic_image_error)
//                .placeholder(R.drawable.ic_image_placeholder)
//                .into(viewHolder.mImageViewMovie);
        viewHolder.mTextViewTitle.setText(mMovieList.get(position).getTitle());
        viewHolder.setMovie(mMovieList.get(position));

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    /**
     * Recyclerview interface to return data when item gets clicked
     */
    public interface MovieItemClickListener {
        void onClick(Movies movie, View view);
    }

    private class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final FrameLayout mFrameLayoutItem;
        final ImageView mImageViewMovie;
        final TextView mTextViewTitle;

        Movies mMovie;

        MovieViewHolder(View itemView) {
            super(itemView);

            mFrameLayoutItem = itemView.findViewById(R.id.frame_item);
            mFrameLayoutItem.setOnClickListener(this);
            mImageViewMovie = itemView.findViewById(R.id.im_movie);
            mTextViewTitle = itemView.findViewById(R.id.tv_title);
        }

        private void setMovie(Movies movie) {
            this.mMovie = movie;
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(mMovie, view);
        }
    }
}
