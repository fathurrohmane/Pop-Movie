package com.elkusnandi.popularmovie.api;

import com.elkusnandi.popularmovie.BuildConfig;
import com.elkusnandi.popularmovie.data.model.CastRespond;
import com.elkusnandi.popularmovie.data.model.Movie;
import com.elkusnandi.popularmovie.data.model.MovieDetail;
import com.elkusnandi.popularmovie.data.model.PostMovie;
import com.elkusnandi.popularmovie.data.model.RequestSessionIdRespond;
import com.elkusnandi.popularmovie.data.model.RequestTokenRespond;
import com.elkusnandi.popularmovie.data.model.Respond;
import com.elkusnandi.popularmovie.data.model.ShowRespond;
import com.elkusnandi.popularmovie.data.model.Tv;
import com.elkusnandi.popularmovie.data.model.TvDetail;
import com.elkusnandi.popularmovie.data.model.UserDetailRespond;
import com.elkusnandi.popularmovie.data.model.Video;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Moviedb class to handle Retrofit observable
 * Created by Taruna 98 on 09/12/2017.
 */

public interface MovieDbApi {

    String BASE_URL = "https://api.themoviedb.org/3/";

    String API = "?api_key=" + BuildConfig.MOVIE_DB_API_KEY;

//  ========== Movie ==========

    @GET("movie/now_playing" + API)
    Single<ShowRespond<Movie>> getNowPlayingMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/popular" + API)
    Single<ShowRespond<Movie>> getPopularMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/latest" + API)
    Single<ShowRespond<Movie>> getRecentlyAddedMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/upcoming" + API)
    Single<ShowRespond<Movie>> getUpcomingMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/{movie_id}" + API)
    Single<MovieDetail> getMovieDetails(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/credits" + API)
    Single<CastRespond> getMovieCasts(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/videos" + API)
    Single<Video> getMovieVideos(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/images" + API)
    Single<ShowRespond<Movie>> getMovieWallpapers(@Path("movie_id") long movieId);

//  ========== Tv =========

    @GET("tv/on_the_air" + API)
    Single<ShowRespond<Tv>> getOnTheAirTvs(@Query("page") int page, @Query("region") String region);

    @GET("tv/popular" + API)
    Single<ShowRespond<Tv>> getPopularTvs(@Query("page") int page, @Query("region") String region);

    @GET("tv/latest" + API)
    Single<ShowRespond<Tv>> getRecentlyAddedTvs(@Query("page") int page, @Query("region") String region);

    @GET("tv/airing_today" + API)
    Single<ShowRespond<Tv>> getAiringTodayTvs(@Query("page") int page, @Query("language") String language);

    @GET("tv/{id}" + API)
    Single<TvDetail> getTvDetails(@Path("id") long id);

    @GET("tv/{id}/credits" + API)
    Single<CastRespond> getTvCasts(@Path("id") long id);

    @GET("tv/{id}/videos" + API)
    Single<Video> getTvVideos(@Path("id") long id);

    @GET("tv/{id}/images" + API)
    Single<ShowRespond<Tv>> getTvWallpapers(@Path("id") long id);

//  ========== Login ==========

    @GET("authentication/token/new" + API)
    Single<RequestTokenRespond> requestToken();

    @GET("authentication/session/new" + API)
    Single<RequestSessionIdRespond> requestSession(@Query("request_token") String requestToken);

//  ========== Account ==========

    @GET("account" + API)
    Single<UserDetailRespond> getUserDetail(@Query("session_id") String sessionId);

    @GET("account/{account_id}/lists" + API)
    Single<RequestTokenRespond> getUserMovieList(@Path("account_id") String accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @GET("account/{account_id}/favorite/movies" + API)
    Single<ShowRespond<Movie>> getUserFavouriteMovies(@Path("account_id") long accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @GET("account/{account_id}/watchlist/movies" + API)
    Single<ShowRespond<Movie>> getUserMovieWatchList(@Path("account_id") long accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @GET("account/{account_id}/favorite/tv" + API)
    Single<ShowRespond<Tv>> getUserFavouriteTvs(@Path("account_id") long accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @GET("account/{account_id}/watchlist/tv" + API)
    Single<ShowRespond<Tv>> getUserTvWatchList(@Path("account_id") long accountId, @Query("session_id") String sessionId, @Query("page") int page);

    @POST("account/{account_id}/favorite" + API)
    Single<Respond> addMovieToFavourite(@Path("account_id") long accountId, @Query("session_id") String sessionId, @Body PostMovie movie);

    @POST("account/{account_id}/watchlist" + API)
    Single<Respond> addMovieToWatchList(@Path("account_id") long accountId, @Query("session_id") String sessionId, @Body PostMovie movie);


}
