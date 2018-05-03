/*
* Copyright (C) 2017 Fathurrohman Elkusnandi
*/

package com.elkusnandi.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.common.base.BaseRecyclerViewAdapter;
import com.elkusnandi.popularmovie.common.interfaces.RecyclerViewItemClickListener;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Taruna 98 on 22/06/2017.
 * Movie Adapter for Movie Recyclerview
 */

public class MovieAdapter extends BaseRecyclerViewAdapter<Movie> {

    private final Context context;
    private RecyclerViewItemClickListener<Movie> onClickListener;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void addItemClickListener(RecyclerViewItemClickListener<Movie> movieItemClickListener) {
        this.onClickListener = movieItemClickListener;
    }

    public void removeItemClickListener() {
        if (onClickListener != null) {
            onClickListener = null;
        }
    }

    public List<Movie> getMovieList() {
        return items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case VIEW_ITEM:
                view = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new MovieViewHolder(view);
                return viewHolder;
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        switch (getItemViewType(position)) {
            case VIEW_ITEM:
                MovieViewHolder viewHolder = (MovieViewHolder) holder;
                String url = items.get(position).getPosterUrl(Movie.PosterSize.w342);
                Picasso.with(context)
                        .load(url)
                        .error(R.drawable.ic_image_error)
                        .placeholder(R.drawable.ic_image_placeholder)
                        .fit().centerCrop()
                        .into(viewHolder.imageViewMovie);
                viewHolder.textViewTitle.setText(items.get(position).getTitle());
                viewHolder.setMovie(items.get(position));
                break;
        }
    }

    private class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final FrameLayout frameLayout;
        final ImageView imageViewMovie;
        final TextView textViewTitle;

        Movie movie;

        MovieViewHolder(View itemView) {
            super(itemView);

            frameLayout = itemView.findViewById(R.id.frame_item);
            frameLayout.setOnClickListener(this);
            imageViewMovie = itemView.findViewById(R.id.im_movie);
            textViewTitle = itemView.findViewById(R.id.tv_title);
        }

        private void setMovie(Movie movie) {
            this.movie = movie;
        }

        @Override
        public void onClick(View view) {
            onClickListener.onItemClick(movie);
        }
    }
}
