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

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.common.interfaces.RecyclerViewItemClickListener;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taruna 98 on 22/06/2017.
 * Movie Adapter for Movie Recyclerview
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Movie> mMovieList;
    private RecyclerViewItemClickListener<Movie> onClickListener;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
        mMovieList = new ArrayList<>();
    }

    public void addItemClickListener(RecyclerViewItemClickListener<Movie> movieItemClickListener) {
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
    public void setData(List<Movie> movieList) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MovieResultDiffUtils(mMovieList, movieList));

        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        result.dispatchUpdatesTo(this);
    }

    public void addMoreData(List<Movie> movieList) {
        int insertPosition = getItemCount();
        this.mMovieList.addAll(movieList);
        this.notifyItemInserted(insertPosition);
    }

    public List<Movie> getMovieList() {
        return mMovieList;
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
        String url = mMovieList.get(position).getPosterUrl(Movie.PosterSize.w342);
//        GlideApp.with(mContext)
//                .load(url)
//                .error(R.drawable.ic_image_error)
//                .fitCenter()
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(viewHolder.mImageViewMovie);
        Picasso.with(mContext)
                .load(url)
                .error(R.drawable.ic_image_error)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(viewHolder.mImageViewMovie);
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

    private class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final FrameLayout mFrameLayoutItem;
        final ImageView mImageViewMovie;
        final TextView mTextViewTitle;

        Movie mMovie;

        MovieViewHolder(View itemView) {
            super(itemView);

            mFrameLayoutItem = itemView.findViewById(R.id.frame_item);
            mFrameLayoutItem.setOnClickListener(this);
            mImageViewMovie = itemView.findViewById(R.id.im_movie);
            mTextViewTitle = itemView.findViewById(R.id.tv_title);
        }

        private void setMovie(Movie movie) {
            this.mMovie = movie;
        }

        @Override
        public void onClick(View view) {
            onClickListener.onItemClick(mMovie, view);
        }
    }

    private class MovieResultDiffUtils extends DiffUtil.Callback {

        private List<Movie> oldList;
        private List<Movie> newList;

        private MovieResultDiffUtils(List<Movie> oldList, List<Movie> newList) {
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

}
