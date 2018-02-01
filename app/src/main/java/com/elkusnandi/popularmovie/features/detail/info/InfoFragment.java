package com.elkusnandi.popularmovie.features.detail.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.CastAdapter;
import com.elkusnandi.popularmovie.common.base.BaseFragment;
import com.elkusnandi.popularmovie.data.model.CastRespond;
import com.elkusnandi.popularmovie.data.model.Genre;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.PostMovie;
import com.elkusnandi.popularmovie.data.model.Respond;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.AndroidSchedulerProvider;
import com.elkusnandi.popularmovie.utils.TextToLinkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class InfoFragment extends BaseFragment implements
        InfoContract.View,
        TextToLinkUtils.SpannableClickListener<Genre> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //    Header Info views
    @BindView(R.id.iv_poster)
    ImageView imageViewPoster;
    @Nullable
    @BindView(R.id.iv_backdrop)
    ImageView imageViewBackdrop;
    @BindView(R.id.tv_title)
    TextView textViewTitle;
    @BindView(R.id.tv_releasedate)
    TextView textViewReleaseDate;
    @BindView(R.id.tv_rating)
    TextView textViewRating;
    @BindView(R.id.tv_language)
    TextView textViewLanguage;
    @BindView(R.id.tv_genre)
    TextView textViewGenre;

    //    Content Info view
    @BindView(R.id.tv_plot)
    TextView textViewPlot;
    @BindView(R.id.rv_casts)
    RecyclerView recyclerViewCast;

    //    Action button
    @BindView(R.id.fab_favourite)
    FloatingActionButton fabFavourite;
    @BindView(R.id.fab_list)
    FloatingActionButton fabList;
    @BindView(R.id.fab_watchlist)
    FloatingActionButton fabWatchList;

    private Movie movie;
    private CastRespond castRespond;
    private MovieDetail movieDetail;
    private InfoPresenter presenter;
    private CastAdapter castAdapter;
    private TextToLinkUtils textToLinkUtils;

    public InfoFragment() {
    }

    public static InfoFragment newInstance(Movie movie) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(ARG_PARAM1);
        }

        presenter = new InfoPresenter(new CompositeDisposable(),
                Repository.getInstance(AndroidSchedulerProvider.getInstance()),
                AndroidSchedulerProvider.getInstance());
        textToLinkUtils = new TextToLinkUtils(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);

        // Initialize view
        Picasso.with(getContext())
                .load(movie.getPosterUrl(Movie.PosterSize.w342))
                .into(imageViewPoster);
        if (imageViewBackdrop != null) {
            Picasso.with(getContext())
                    .load(movie.getBackdropUrl(Movie.PosterSize.w342))
                    .fit()
                    .centerCrop()
                    .into(imageViewBackdrop);
        }

        textViewTitle.setText(movie.getTitle());
        textViewPlot.setText(movie.getOverview());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewLanguage.setText(movie.getOriginalLanguage());
        textViewLanguage.setText(movie.getOriginalLanguage());
        textViewReleaseDate.setText(movie.getReleaseDate());
        String rating = String.valueOf(movie.getVoteAverage());
        textViewRating.setText(rating);

        // set button fab

        // Init presenter
        presenter.onAttach(this);
        presenter.loadCast(movie.getId());
        presenter.loadInfo(movie.getId());

        castAdapter = new CastAdapter(getContext());
        recyclerViewCast.setAdapter(castAdapter);
        recyclerViewCast.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @OnClick({R.id.fab_favourite, R.id.fab_watchlist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_favourite:
                if (getActivity() != null && getContext() != null) {
                    String sessionId = getActivity()
                            .getSharedPreferences(getString(R.string.sharedpreference_id), Context.MODE_PRIVATE)
                            .getString(getString(R.string.sharedpreference_session_id), "");
                    long accountId = getActivity()
                            .getSharedPreferences(getString(R.string.sharedpreference_id), Context.MODE_PRIVATE)
                            .getLong(getString(R.string.sharedpreference_account_id), -1L);
                    presenter.addToFavourite(accountId, sessionId, new PostMovie(PostMovie.TYPE_MOVIE, movie.getId(), true));
                    fabFavourite.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favourite));
                }
                break;
            case R.id.fab_watchlist:
                if (getActivity() != null && getContext() != null) {
                    String sessionId = getActivity()
                            .getSharedPreferences(getString(R.string.sharedpreference_id), Context.MODE_PRIVATE)
                            .getString(getString(R.string.sharedpreference_session_id), "");
                    long accountId = getActivity()
                            .getSharedPreferences(getString(R.string.sharedpreference_id), Context.MODE_PRIVATE)
                            .getLong(getString(R.string.sharedpreference_account_id), -1L);
                    presenter.addToWatchList(accountId, sessionId, new PostMovie(PostMovie.TYPE_MOVIE, movie.getId(), true));
                    fabWatchList.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_playlist_check));
                }
                break;
        }
    }

    @Override
    public void showRespond(Respond respond) {
        super.showRespond(respond);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void infoLoaded(MovieDetail movieDetail) {
        if (movieDetail.getGenres() != null) {
            if (movieDetail.getGenres().size() > 0) {
                textToLinkUtils.createSpannableString(movieDetail.getGenres(), textViewGenre);
            } else {
                textViewGenre.setText("-");
            }
        }
    }

    @Override
    public void castLoaded(CastRespond castRespond) {
        castAdapter.addData(castRespond.getCast());
    }

    @Override
    public boolean isDataReady() {
        return castRespond != null && movieDetail != null;
    }

    @Override
    public void onLinkClicked(Genre itemClicked) {
        Toast.makeText(getContext(), itemClicked.getId() + " " + itemClicked.getName(), Toast.LENGTH_LONG).show();
    }
}
