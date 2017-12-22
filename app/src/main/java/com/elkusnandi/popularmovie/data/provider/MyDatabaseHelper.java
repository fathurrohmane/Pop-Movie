package com.elkusnandi.popularmovie.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Taruna 98 on 30/07/2017.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABSE_NAME = "database.db";
    private static final int VERSION = 5;

    public MyDatabaseHelper(Context context) {
        super(context, DATABSE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE_FAVORITE = "CREATE TABLE " +
                DatabaseContract.FavoriteMoviesTable.TABLE_NAME + " (" +
                DatabaseContract.FavoriteMoviesTable._ID + " INTEGER PRIMARY KEY, " +
                DatabaseContract.FavoriteMoviesTable.COLUMN_ID + " INTEGER NOT NULL, " +
                DatabaseContract.FavoriteMoviesTable.COLUMN_POSTER + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteMoviesTable.COLUMN_TITLE + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteMoviesTable.COLUMN_SYNOPSIS + " TEXT NOT NULL, " +
                DatabaseContract.FavoriteMoviesTable.COLUMN_RATING + " DOUBLE NOT NULL, " +
                DatabaseContract.FavoriteMoviesTable.COLUMN_RELEASEDATE + " TEXT NOT NULL ); ";

        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavoriteMoviesTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
