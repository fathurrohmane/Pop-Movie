package com.elkusnandi.popularmovie.api;

import com.elkusnandi.popularmovie.data.model.MovieCasts;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.MovieResult;
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

    @GET("discover/{type}")
    Single<MovieResult> getPopularMovies(@Path("type") String type, @Query("api_key") String apiKey);

    @GET("movie/{movieId}")
    Single<MovieResult> getMovieDetailInfo(@Path("type") long movieId, @Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Single<MovieResult> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("region") String region);

    @GET("movie/popular")
    Single<MovieResult> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("region") String region);

    @GET("movie/latest")
    Single<MovieResult> getRecentlyAddedMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("region") String region);

    @GET("movie/upcoming")
    Single<MovieResult> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("region") String region);

    @GET("movie/{movie_id}")
    Single<MovieDetail> getMovieDetails(@Path("movie_id") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Single<MovieCasts> getMovieCasts(@Path("movie_id") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Single<Video> getMovieVideos(@Path("movie_id") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/images")
    Single<MovieResult> getMovieWallpapers(@Path("movie_id") long movieId, @Query("api_key") String apiKey);

//  ========== Login ==========

    @GET("authentication/token/new")
    Single<RequestTokenRespond> requestToken(@Query("api_key") String apiKey);

    @GET("authentication/session/new")
    Single<RequestSessionIdRespond> requestSession(@Query("api_key") String apiKey, @Query("request_token") String requestToken);

//  ========== Account ==========

    @GET("account/")
    Single<RequestTokenRespond> getUserDetail(@Query("api_key") String apiKey, @Query("session_id") String sessionId);

    @GET("account/{account_id}/lists")
    Single<RequestTokenRespond> getUserMovieList(@Query("api_key") String apiKey, @Path("account_id") String accountId, @Query("session_id") String sessionId, @Query("page") int page);

    // TODO: 30/12/2017 change data type
    @GET("account/{account_id}/favorite/movies")
    Single<RequestTokenRespond> getUserFavouriteMovie(@Query("api_key") String apiKey, @Path("account_id") String accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @GET("account/{account_id}/watchlist/movies")
    Single<RequestTokenRespond> getUserWatchList(@Query("api_key") String apiKey, @Path("account_id") String accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @POST("account/{account_id}/favorite")
    Single<RequestTokenRespond> addMovieToFavourite(@Query("api_key") String apiKey, @Query("session_id") String sessionId);

    @POST("account/{account_id}/watchlist")
    Single<RequestTokenRespond> addMovieToWatchList(@Query("api_key") String apiKey, @Query("session_id") String sessionId);


}
