package com.elkusnandi.popularmovie.api;

import com.elkusnandi.popularmovie.BuildConfig;
import com.elkusnandi.popularmovie.data.model.MovieCasts;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.MovieRespond;
import com.elkusnandi.popularmovie.data.model.RequestSessionIdRespond;
import com.elkusnandi.popularmovie.data.model.RequestTokenRespond;
import com.elkusnandi.popularmovie.data.model.Video;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Taruna 98 on 09/12/2017.
 */

public interface MovieDbApi {

    String BASE_URL = "https://api.themoviedb.org/3/";

    String API = "?api_key=" + BuildConfig.MOVIE_DB_API_KEY;

    @GET("discover/{type}" + API)
    Single<MovieRespond> getPopularMovies(@Path("type") String type);

    @GET("movie/{movieId}" + API)
    Single<MovieRespond> getMovieDetailInfo(@Path("type") long movieId);

    @GET("movie/now_playing" + API)
    Single<MovieRespond> getNowPlayingMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/popular" + API)
    Single<MovieRespond> getPopularMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/latest" + API)
    Single<MovieRespond> getRecentlyAddedMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/upcoming" + API)
    Single<MovieRespond> getUpcomingMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/{movie_id}" + API)
    Single<MovieDetail> getMovieDetails(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/credits" + API)
    Single<MovieCasts> getMovieCasts(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/videos" + API)
    Single<Video> getMovieVideos(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/images" + API)
    Single<MovieRespond> getMovieWallpapers(@Path("movie_id") long movieId);

//  ========== Login ==========

    @GET("authentication/token/new" + API)
    Single<RequestTokenRespond> requestToken();

    @GET("authentication/session/new" + API)
    Single<RequestSessionIdRespond> requestSession(@Query("request_token") String requestToken);

//  ========== Account ==========

    @GET("account/" + API)
    Single<RequestTokenRespond> getUserDetail(@Query("session_id") String sessionId);

    @GET("account/{account_id}/lists" + API)
    Single<RequestTokenRespond> getUserMovieList(@Path("account_id") String accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @GET("account/{account_id}/favorite/movies" + API)
    Single<MovieRespond> getUserFavouriteMovies(@Path("account_id") String accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @GET("account/{account_id}/watchlist/movies" + API)
    Single<RequestTokenRespond> getUserWatchList(@Path("account_id") String accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @POST("account/{account_id}/favorite" + API)
    Single<RequestTokenRespond> addMovieToFavourite(@Query("session_id") String sessionId);

    @POST("account/{account_id}/watchlist" + API)
    Single<RequestTokenRespond> addMovieToWatchList(@Query("session_id") String sessionId);


}
