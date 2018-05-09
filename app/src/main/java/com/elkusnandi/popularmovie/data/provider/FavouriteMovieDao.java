package com.elkusnandi.popularmovie.data.provider;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.elkusnandi.popularmovie.data.model.FavouriteMovieEntity;

import java.util.List;

@Dao
public interface FavouriteMovieDao {

    @Query("SELECT * FROM favourite_movie")
    List<FavouriteMovieEntity> getAll();

    @Query("SELECT EXISTS(SELECT * FROM favourite_movie WHERE id LIKE (:movie_id))")
    boolean isFavourite(int movie_id);

    @Insert
    long addToFavouriteMovieList(FavouriteMovieEntity favouriteMovieEntity);

    @Delete
    void removeFromFavouriteMovieList(FavouriteMovieEntity favouriteMovieEntity);

    @Query("DELETE FROM favourite_movie")
    void deleteAll();
}
