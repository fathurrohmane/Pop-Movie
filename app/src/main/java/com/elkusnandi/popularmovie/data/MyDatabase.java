package com.elkusnandi.popularmovie.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.elkusnandi.popularmovie.data.provider.AppDatabase;

public class MyDatabase {

    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            //appDatabase = Room.databaseBuilder(context, AppDatabase.class, "my_database").build();
        }
        return appDatabase;
    }
}
