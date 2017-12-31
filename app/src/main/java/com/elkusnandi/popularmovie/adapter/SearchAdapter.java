package com.elkusnandi.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.model.Movies;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Taruna 98 on 26/12/2017.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private static final String TAG = SearchAdapter.class.getSimpleName();
    private Context mContext;
    private List<Movies> mList;

    public SearchAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_movie_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movies item = mList.get(position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster)
        ImageView imageViewPoster;
        @BindView(R.id.tv_title)
        TextView textViewTitle;
        @BindView(R.id.tv_overview)
        TextView textViewOverview;
        @BindView(R.id.tv_rating)
        TextView textViewRating;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}