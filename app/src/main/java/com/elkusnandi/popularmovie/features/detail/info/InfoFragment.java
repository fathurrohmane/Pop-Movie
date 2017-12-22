package com.elkusnandi.popularmovie.features.detail.info;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.adapter.CastAdapter;
import com.elkusnandi.popularmovie.data.model.MovieCasts;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.Movies;
import com.elkusnandi.popularmovie.data.provider.Repository;
import com.elkusnandi.popularmovie.utils.AndroidSchedulerProvider;
import com.elkusnandi.popularmovie.utils.MyDisposable;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoFragment extends Fragment implements InfoContract.View {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.iv_poster)
    ImageView imageViewPoster;
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
    @BindView(R.id.tv_status)
    TextView textViewStatus;
    @BindView(R.id.tv_genre)
    TextView textViewGenre;
    @BindView(R.id.tv_plot)
    TextView textViewPlot;
    @BindView(R.id.rv_casts)
    RecyclerView recyclerViewCast;

    private Movies movie;
    private MovieCasts movieCasts;
    private MovieDetail movieDetail;
    private InfoPresenter presenter;
    private CastAdapter castAdapter;

    private OnFragmentInteractionListener mListener;

    public InfoFragment() {
    }

    public static InfoFragment newInstance(Movies movies) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, movies);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(ARG_PARAM1);
        }

        presenter = new InfoPresenter(MyDisposable.getInstance(),
                Repository.getInstance(AndroidSchedulerProvider.getInstance()),
                AndroidSchedulerProvider.getInstance());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);

        // Initialize view
        Picasso.with(getContext())
                .load(movie.getPosterUrl(Movies.PosterSize.w342))
                .into(imageViewPoster);
        Picasso.with(getContext())
                .load(movie.getBackdropUrl(Movies.PosterSize.w342))
                .fit()
                .centerCrop()
                .into(imageViewBackdrop);
        textViewTitle.setText(movie.getTitle());
        textViewPlot.setText(movie.getOverview());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewGenre.setText("Comedy");
        textViewLanguage.setText(movie.getOriginalLanguage());
        textViewLanguage.setText(movie.getOriginalLanguage());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewStatus.setText("Released");
        String rating = String.valueOf(movie.getVoteAverage());
        textViewRating.setText(rating);

        // Init presenter
        presenter.onAttach(this);
        presenter.loadCast(movie.getId());
        presenter.loadInfo(movie.getId());

        castAdapter = new CastAdapter(getContext());
        recyclerViewCast.setAdapter(castAdapter);
        recyclerViewCast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void infoLoaded(MovieDetail movieDetail) {
        if (movieDetail.getGenres() != null) {
            textViewGenre.setText(movieDetail.getGenres().get(0).getName()); // TODO: 19/12/2017 will throw error
        }
    }

    @Override
    public void castLoaded(MovieCasts movieCasts) {
        castAdapter.addData(movieCasts.getCast());
    }

    @Override
    public boolean isDataReady() {
        return movieCasts != null && movieDetail != null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
