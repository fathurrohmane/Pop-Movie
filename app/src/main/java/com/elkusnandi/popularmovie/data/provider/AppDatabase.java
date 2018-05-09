package com.elkusnandi.popularmovie.data.provider;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.elkusnandi.popularmovie.data.model.FavouriteMovieEntity;

@Database(entities = {FavouriteMovieEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "database";

    public abstract FavouriteMovieDao favouriteMovieDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }
}
