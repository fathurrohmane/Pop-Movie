package com.elkusnandi.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.model.VideoResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Taruna 98 on 19/12/2017.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private static final String TAG = VideoAdapter.class.getSimpleName();
    private Context mContext;
    private List<VideoResult> mList;
    private OnItemClickListener mListener;

    public VideoAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public void setData(List<VideoResult> videos) {
        this.mList.addAll(videos);
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoResult item = mList.get(position);

        String youtubePath = "https://img.youtube.com/vi/" + item.getKey() + "/0.jpg";
        Picasso.get()
                .load(youtubePath)
                .error(R.drawable.ic_image_error)
                .placeholder(R.drawable.ic_image_placeholder)
                .fit().centerCrop()
                .into(holder.imageViewThumbnail);
        holder.textViewTitle.setText(item.getName());
        holder.textViewType.setText(item.getType());
        holder.textViewPlatform.setText(item.getSite());

        holder.itemView.setOnClickListener(v -> mListener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(VideoResult video);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_thumbnail)
        ImageView imageViewThumbnail;
        @BindView(R.id.tv_title)
        TextView textViewTitle;
        @BindView(R.id.tv_type)
        TextView textViewType;
        @BindView(R.id.tv_platform)
        TextView textViewPlatform;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}