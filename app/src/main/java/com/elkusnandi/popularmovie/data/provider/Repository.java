package com.elkusnandi.popularmovie.data.provider;

import com.elkusnandi.popularmovie.api.MovieDbApi;
import com.elkusnandi.popularmovie.data.model.MovieCasts;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.MovieRespond;
import com.elkusnandi.popularmovie.data.model.RequestSessionIdRespond;
import com.elkusnandi.popularmovie.data.model.RequestTokenRespond;
import com.elkusnandi.popularmovie.data.model.Video;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Taruna 98 on 12/12/2017.
 */

public class Repository {

    private static Repository INSTANCE;

    private Retrofit retrofit;

    private Repository(BaseSchedulerProvider schedulerProvider) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(MovieDbApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io()))
                .build();
    }

    public static Repository getInstance(BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(schedulerProvider);
        }

        return INSTANCE;
    }

    private MovieDbApi getApiService() {
        return retrofit.create(MovieDbApi.class);
    }

    public Single<MovieRespond> getTopMovies(String type, String api) {
        switch (type) {
            case "now_playing":

            case "up_coming":
                return getApiService().getPopularMovies(type);
            case "popular":
                return getApiService().getPopularMovies(type);
            case "recently_added":
                return getApiService().getPopularMovies(type);
            default:
                return getApiService().getPopularMovies("movie");
        }
    }

    public Single<MovieRespond> getNowPlayingMovies(int page, String region) {
        return getApiService().getNowPlayingMovies(page, region);
    }

    public Single<MovieRespond> getUpComingMovies(int page, String region) {
        return getApiService().getUpcomingMovies(page, region);
    }

    public Single<MovieRespond> getPopularMovies(int page, String region) {
        return getApiService().getPopularMovies(page, region);
    }

    public Single<MovieRespond> getRecentlyAddedMovies(int page, String region) {
        return getApiService().getRecentlyAddedMovies(page, region);
    }

    public Single<MovieCasts> getMovieCasts(long movieId) {
        return getApiService().getMovieCasts(movieId);
    }

    public Single<MovieDetail> getMovieDetails(long movieId) {
        return getApiService().getMovieDetails(movieId);
    }

    public Single<Video> getMovieVideos(long movieId) {
        return getApiService().getMovieVideos(movieId);
    }

    /**
     * Get request token respond for log in
     *
     * @return
     */
    public Single<RequestTokenRespond> requestToken() {
        return getApiService().requestToken();
    }

    /**
     * Get session id for accessing user data
     *
     * @param requestToken
     * @return
     */
    public Single<RequestSessionIdRespond> requestSessionId(String requestToken) {
        return getApiService().requestSession(requestToken);
    }

}
