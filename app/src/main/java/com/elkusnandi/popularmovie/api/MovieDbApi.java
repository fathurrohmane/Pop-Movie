package com.elkusnandi.popularmovie.api;

import com.elkusnandi.popularmovie.data.model.MovieCasts;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.MovieResult;
import com.elkusnandi.popularmovie.data.model.Video;

import io.reactivex.Single;
import retrofit2.http.GET;
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
}
