package com.elkusnandi.popularmovie.data.provider;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.elkusnandi.popularmovie.R;
import com.elkusnandi.popularmovie.data.model.FavouriteMovieEntity;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.data.model.ShowRespond;
import com.elkusnandi.popularmovie.utils.AndroidSchedulerProvider;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class FavouriteMovieListService extends IntentService {

    public static final String NAME = "favourite_movie_list_service";
    public static final String TAG = FavouriteMovieListService.class.getSimpleName();

    public FavouriteMovieListService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // get user id and session id
        SharedPreferences sharedPreferences =
                getApplicationContext().getSharedPreferences(getString(R.string.sharedpreference_id), Context.MODE_PRIVATE);
        long accountId = sharedPreferences.getLong(getString(R.string.sharedpreference_account_id), -1L);
        String sessionId = sharedPreferences.getString(getString(R.string.sharedpreference_session_id), "");

        // get database instance
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());

        // check login status
        if (accountId < 0L || sessionId.isEmpty()) {
            appDatabase.favouriteMovieDao().deleteAll();
            return;
        }

        // get repository instance
        AndroidSchedulerProvider androidSchedulerProvider = AndroidSchedulerProvider.getInstance();
        Repository repository = Repository.getInstance(AppDatabase.getInstance(getApplicationContext()), androidSchedulerProvider);
        Single<ShowRespond<Movie>> disposableMovie = repository.getUserFavouriteMovies(accountId, sessionId, 1);

        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(
                disposableMovie
                        .subscribeOn(androidSchedulerProvider.io())
                        .observeOn(androidSchedulerProvider.io())
                        .subscribe((movieShowRespond, throwable) -> {
                            if (throwable != null && movieShowRespond == null) {
                                // error handling
                                Log.e(TAG, "Error retrieving FavouriteMovieList");
                                return;
                            }
                            // delete all row in database to match online database
                            appDatabase.favouriteMovieDao().deleteAll();

                            // save it
                            for (Movie movie : movieShowRespond.getResults()) {
                                appDatabase.favouriteMovieDao().addToFavouriteMovieList(new FavouriteMovieEntity(movie.getId()));
                            }
                            Log.v(TAG, "Successfully retrieving FavouriteMovieList");
                        })
        );
    }
}
