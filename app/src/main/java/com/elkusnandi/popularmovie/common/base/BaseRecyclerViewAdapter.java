package com.elkusnandi.popularmovie.common.base;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.common.interfaces.RecyclerViewItemInfoState;
import com.elkusnandi.popularmovie.ui.widget.InfoViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taruna 98 on 3/8/2018.
 */

public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_PER_PAGE = 10;
    public static final int ITEM_SPAN = 3;

    public interface RecyclerViewItemListener<T> {

        void onReloadRecyclerView();

        void onItemClick(T data);

    }

    protected final int VIEW_ITEM = 0;
    protected final int VIEW_INFO_LOADING = 1;
    protected final int VIEW_INFO_RELOAD = 2;
    protected final int VIEW_INFO_BOTTOM_PAGE = 3;

    protected List<T> items;
    protected RecyclerViewItemInfoState infoState;
    protected RecyclerViewItemListener<T> listener;

    public BaseRecyclerViewAdapter() {
        this.items = new ArrayList<>();
        infoState = RecyclerViewItemInfoState.loading;

    }

    public void setRecyclerViewItemListener(RecyclerViewItemListener<T> listener) {
        this.listener = listener;
    }

    public void addData(List<T> items) {
        int lastPos = getItemCount() - 1;
        this.items.addAll(items);
        notifyItemMoved(lastPos, getItemCount() - 1);
        notifyItemInserted(getItemCount());

        infoState = RecyclerViewItemInfoState.loading;
    }

    public void setInfoItemState(RecyclerViewItemInfoState infoItemState) {
        this.infoState = infoItemState;
        notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case VIEW_INFO_LOADING:
            case VIEW_INFO_RELOAD:
            case VIEW_INFO_BOTTOM_PAGE:
                view = inflater.inflate(R.layout.item_information, parent, false);
                InfoViewHolder infoViewHolder = new InfoViewHolder(view);
                infoViewHolder.setReloadButtonOnClickListener(onClickListener);
                return infoViewHolder;
            default:
                throw new IllegalArgumentException("Illegal View Holder type " + viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InfoViewHolder infoViewHolder;
        switch (getItemViewType(position)) {
            case VIEW_INFO_LOADING:
                infoViewHolder = (InfoViewHolder) holder;
                infoViewHolder.progressBar.show();
                infoViewHolder.reloadButton.setVisibility(View.INVISIBLE);
                infoViewHolder.textViewInfo.setVisibility(View.INVISIBLE);
                break;
            case VIEW_INFO_RELOAD:
                infoViewHolder = (InfoViewHolder) holder;
                infoViewHolder.progressBar.hide();
                infoViewHolder.reloadButton.setVisibility(View.VISIBLE);
                infoViewHolder.textViewInfo.setVisibility(View.VISIBLE);
                infoViewHolder.textViewInfo.setText("No Connection!");
                break;
            case VIEW_INFO_BOTTOM_PAGE:
                infoViewHolder = (InfoViewHolder) holder;
                infoViewHolder.progressBar.hide();
                infoViewHolder.reloadButton.setVisibility(View.GONE);
                infoViewHolder.textViewInfo.setVisibility(View.VISIBLE);
                infoViewHolder.textViewInfo.setText("Bottom of page");
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            switch (infoState) {
                case loading:
                    return VIEW_INFO_LOADING;
                case reload:
                    return VIEW_INFO_RELOAD;
                case bottom_of_page:
                    return VIEW_INFO_BOTTOM_PAGE;
                default:
                    return VIEW_INFO_LOADING;
            }
        }
        return VIEW_ITEM;
    }

    @Override
    public int getItemCount() {
        if (items.size() > 0) {
            return items.size() + 1;
        } else {
            return items.size();
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_reload:
                    listener.onReloadRecyclerView();
                    break;
            }
        }
    };
}
