package com.elkusnandi.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.model.Cast;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Taruna 98 on 19/12/2017.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private static final String TAG = CastAdapter.class.getSimpleName();
    private Context mContext;
    private List<Cast> list;

    public CastAdapter(Context context) {
        this.mContext = context;
        this.list = new ArrayList<>();
    }

    public void addData(List<Cast> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_cast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cast item = list.get(position);

        Picasso.get()
                .load(item.getProfilePath(Movie.PosterSize.w342))
                .placeholder(R.drawable.ic_cast_placeholder)
                .error(R.drawable.ic_cast_placeholder)
                .into(holder.imageViewCast);

        holder.textViewCastName.setText(item.getName());
        holder.textViewCastMovieName.setText(item.getCharacter());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cast)
        ImageView imageViewCast;
        @BindView(R.id.tv_cast_name)
        TextView textViewCastName;
        @BindView(R.id.tv_cast_movie_name)
        TextView textViewCastMovieName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}