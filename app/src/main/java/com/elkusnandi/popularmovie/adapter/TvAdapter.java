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
import com.elkusnandi.popularmovie.data.model.Tv;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taruna 98 on 13/01/2018.
 */

public class TvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Tv> mShowList;
    private RecyclerViewItemClickListener<Tv> onClickListener;

    public TvAdapter(Context mContext) {
        this.mContext = mContext;
        mShowList = new ArrayList<>();
    }

    public void addItemClickListener(RecyclerViewItemClickListener<Tv> showListItemClickListener) {
        this.onClickListener = showListItemClickListener;

    }

    public void removeItemClickListener() {
        if (onClickListener != null) {
            onClickListener = null;
        }
    }

    /**
     * Set Movie data for recyclerview
     */
    public void setData(List<Tv> tvList) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new TvAdapter.TvResultDiffUtils(mShowList, tvList));

        this.mShowList.clear();
        this.mShowList.addAll(tvList);
        result.dispatchUpdatesTo(this);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new TvViewHolder(view);
                break;
            default:
                view = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new TvViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TvAdapter.TvViewHolder viewHolder = (TvAdapter.TvViewHolder) holder;
        String url = mShowList.get(position).getPosterUrl(Movie.PosterSize.w342);

        Picasso.get()
                .load(url)
                .error(R.drawable.ic_image_error)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(viewHolder.mImageViewMovie);
        viewHolder.mTextViewTitle.setText(mShowList.get(position).getName());
        viewHolder.setTv(mShowList.get(position));

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mShowList.size();
    }

    private class TvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final FrameLayout mFrameLayoutItem;
        final ImageView mImageViewMovie;
        final TextView mTextViewTitle;

        private Tv tv;

        TvViewHolder(View itemView) {
            super(itemView);

            mFrameLayoutItem = itemView.findViewById(R.id.frame_item);
            mFrameLayoutItem.setOnClickListener(this);
            mImageViewMovie = itemView.findViewById(R.id.im_movie);
            mTextViewTitle = itemView.findViewById(R.id.tv_title);
        }

        private void setTv(Tv tv) {
            this.tv = tv;
        }

        @Override
        public void onClick(View view) {
            onClickListener.onItemClick(tv);
        }
    }

    private class TvResultDiffUtils extends DiffUtil.Callback {

        private List<Tv> oldList;
        private List<Tv> newList;

        private TvResultDiffUtils(List<Tv> oldList, List<Tv> newList) {
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