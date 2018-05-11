package com.elkusnandi.popularmovie.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favourite_movie")
public class FavouriteMovieEntity {
    @PrimaryKey
    private int id;

    public FavouriteMovieEntity() {
    }

    @Ignore
    public FavouriteMovieEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
