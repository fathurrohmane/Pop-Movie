package com.elkusnandi.popularmovie.data.provider;

import com.elkusnandi.popularmovie.api.MovieDbApi;
import com.elkusnandi.popularmovie.data.model.MovieCasts;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.MovieResult;
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

    public Single<MovieResult> getTopMovies(String type, String api) {
        switch (type) {
            case "now_playing":

            case "up_coming":
                return getApiService().getPopularMovies(type, api);
            case "popular":
                return getApiService().getPopularMovies(type, api);
            case "recently_added":
                return getApiService().getPopularMovies(type, api);
            default:
                return getApiService().getPopularMovies("movie", api);
        }
    }

    public Single<MovieResult> getNowPlayingMovies(String api, int page, String region) {
        return getApiService().getNowPlayingMovies(api, page, region);
    }

    public Single<MovieResult> getUpComingMovies(String api, int page, String region) {
        return getApiService().getUpcomingMovies(api, page, region);
    }

    public Single<MovieResult> getPopularMovies(String api, int page, String region) {
        return getApiService().getPopularMovies(api, page, region);
    }

    public Single<MovieResult> getRecentlyAddedMovies(String api, int page, String region) {
        return getApiService().getRecentlyAddedMovies(api, page, region);
    }

    public Single<MovieCasts> getMovieCasts(String api, long movieId) {
        return getApiService().getMovieCasts(movieId, api);
    }

    public Single<MovieDetail> getMovieDetails(String api, long movieId) {
        return getApiService().getMovieDetails(movieId, api);
    }

    public Single<Video> getMovieVideos(String api, long movieId) {
        return getApiService().getMovieVideos(movieId, api);
    }

}
