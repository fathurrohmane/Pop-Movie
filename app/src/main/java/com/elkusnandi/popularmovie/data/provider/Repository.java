package com.elkusnandi.popularmovie.data.provider;

import com.elkusnandi.popularmovie.api.MovieDbApi;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.data.model.MovieCasts;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.PostMovie;
import com.elkusnandi.popularmovie.data.model.RequestSessionIdRespond;
import com.elkusnandi.popularmovie.data.model.RequestTokenRespond;
import com.elkusnandi.popularmovie.data.model.Respond;
import com.elkusnandi.popularmovie.data.model.ShowRespond;
import com.elkusnandi.popularmovie.data.model.UserDetailRespond;
import com.elkusnandi.popularmovie.data.model.Video;
import com.elkusnandi.popularmovie.utils.BaseSchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Repository class for handling local and remote network operation
 * Created by Taruna 98 on 12/12/2017.
 */

public class Repository {

    public static final String MOVIE_TYPE_NOW_PLAYING = "now_playing";
    public static final String MOVIE_TYPE_UP_COMING = "up_coming";
    public static final String MOVIE_TYPE_POPULAR = "popular";
    public static final String MOVIE_TYPE_RECENTLY_ADDED = "recently_added";
    public static final String MOVIE_TYPE_FAVOURITE = "favourite";
    public static final String MOVIE_TYPE_WATCH_LIST = "watch_list";
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

    public Single<ShowRespond<Movie>> getMovies(String type, int page, String region) {
        switch (type) {
            case MOVIE_TYPE_NOW_PLAYING:
                return getApiService().getNowPlayingMovies(page, region);
            case MOVIE_TYPE_UP_COMING:
                return getApiService().getUpcomingMovies(page, region);
            case MOVIE_TYPE_POPULAR:
                return getApiService().getPopularMovies(page, region);
            case MOVIE_TYPE_RECENTLY_ADDED:
                return getApiService().getRecentlyAddedMovies(page, region);
            default:
                throw new IllegalArgumentException("Discover Type Not Found");
        }
    }

    /**
     * Get user favourite movie list
     *
     * @param accountId movie db account id
     * @param sessionId movie db session id
     * @param page      page
     * @return Rx Single of ShowRespond
     */
    public Single<ShowRespond<Movie>> getUserFavouriteMovies(long accountId, String sessionId, int page) {
        return getApiService().getUserFavouriteMovies(accountId, sessionId, page);
    }

    /**
     * Get user watch list
     *
     * @param accountId movie db account id
     * @param sessionId movie db session id
     * @param page      page
     * @return Rx Single of ShowRespond
     */
    public Single<ShowRespond<Movie>> getUserWatchList(long accountId, String sessionId, int page) {
        return getApiService().getUserWatchList(accountId, sessionId, page);
    }

    public Single<ShowRespond<Movie>> getNowPlayingMovies(int page, String region) {
        return getApiService().getNowPlayingMovies(page, region);
    }

    public Single<ShowRespond<Movie>> getUpComingMovies(int page, String region) {
        return getApiService().getUpcomingMovies(page, region);
    }

    public Single<ShowRespond<Movie>> getPopularMovies(int page, String region) {
        return getApiService().getPopularMovies(page, region);
    }

    public Single<ShowRespond<Movie>> getRecentlyAddedMovies(int page, String region) {
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
     * @return Obervable of RequestTokenRespond
     */
    public Single<RequestTokenRespond> requestToken() {
        return getApiService().requestToken();
    }

    /**
     * Get session id for accessing user data
     *
     * @param requestToken request token from moviedb api
     * @return Obervable of RequestSessionIdRespond
     */
    public Single<RequestSessionIdRespond> requestSessionId(String requestToken) {
        return getApiService().requestSession(requestToken);
    }

    /**
     * Get user detail such as Name, User id, avatar etc
     *
     * @param sessionId sessionId from moviedb (get it from MovieDBApi.requestSessionId())
     * @return single observable of UserDetailRespond
     */
    public Single<UserDetailRespond> requestUserDetail(String sessionId) {
        return getApiService().getUserDetail(sessionId);
    }

    /**
     * @param accountId user account id
     * @param sessionId user session id
     * @param movie     movie to add to favourite list
     * @return Respond with respond code
     */
    public Single<Respond> addMovieToFavourite(long accountId, String sessionId, PostMovie movie) {
        return getApiService().addMovieToFavourite(accountId, sessionId, movie);
    }

    /**
     * @param accountId user accound id
     * @param sessionId user session id
     * @param movie     movie to add to watchlist
     * @return Respond with respond code
     */
    public Single<Respond> addMovieToWatchList(long accountId, String sessionId, PostMovie movie) {
        return getApiService().addMovieToWatchList(accountId, sessionId, movie);
    }

}
