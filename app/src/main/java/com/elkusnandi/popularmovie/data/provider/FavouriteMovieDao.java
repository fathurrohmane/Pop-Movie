package com.elkusnandi.popularmovie.data.provider;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.elkusnandi.popularmovie.data.model.FavouriteMovieEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface FavouriteMovieDao {

    @Query("SELECT * FROM favourite_movie")
    Single<List<FavouriteMovieEntity>> getAll();

    @Query("SELECT EXISTS(SELECT * FROM favourite_movie WHERE id LIKE (:movie_id))")
    Single<Boolean> isFavourites(int movie_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] addToFavouriteMovieList(FavouriteMovieEntity... favouriteMovieEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addToFavouriteMovieList(FavouriteMovieEntity favouriteMovieEntity);

    @Delete
    int removeFromFavouriteMovieList(FavouriteMovieEntity favouriteMovieEntity);

    @Query("DELETE FROM favourite_movie")
    int deleteAll();
}
