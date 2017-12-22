package com.elkusnandi.popularmovie.ui.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elkusnandi.popularmovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Taruna 98 on 21/12/2017.
 */

public class InformationView extends RelativeLayout {

    @BindView(R.id.iv_info)
    ImageView imageViewInfo;
    @BindView(R.id.tv_message)
    TextView imageViewMessage;
    @BindView(R.id.bt_action)
    TextView buttonAction;

    private int noDataDrawable;
    private int noDataMessage;
    private int noConnectionDrawable;
    private int noConnectionMessage;
    private int noConnectionButtonText;

    public InformationView(Context context) {
        super(context);
        init();
    }

    public InformationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InformationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.view_information, this);
        ButterKnife.bind(this, view);
        noDataDrawable = R.drawable.ic_info;
        noDataMessage = R.string.error_no_data;
        noConnectionDrawable = R.drawable.ic_internet_off;
        noConnectionMessage = R.string.error_no_connection;
        noConnectionButtonText = R.string.error_no_connection_button;

    }

    public void setResources(@DrawableRes int noDataDrawable,
                             @StringRes int noDataMessage,
                             @DrawableRes int noConnectionDrawable,
                             @StringRes int noConnectionMessage,
                             @StringRes int noConnectionButtonText) {

        this.noDataDrawable = noDataDrawable;
        this.noDataMessage = noDataMessage;
        this.noConnectionDrawable = noConnectionDrawable;
        this.noConnectionMessage = noConnectionMessage;
        this.noConnectionButtonText = noConnectionButtonText;

    }

    public void showNoData() {
        this.setVisibility(VISIBLE);
        imageViewInfo.setImageResource(noDataDrawable);
        imageViewMessage.setText(noDataMessage);
        buttonAction.setVisibility(View.INVISIBLE);
    }

    public void showNoConnection() {
        this.setVisibility(VISIBLE);
        imageViewInfo.setImageResource(noConnectionDrawable);
        imageViewMessage.setText(noConnectionMessage);
        buttonAction.setText(noConnectionButtonText);
        buttonAction.setVisibility(View.VISIBLE);
    }

    public void hide() {
        this.setVisibility(View.INVISIBLE);
    }

    public void addButtonListener(OnClickListener onClickListener) {
        buttonAction.setOnClickListener(onClickListener);
    }

}
