package com.elkusnandi.popularmovie;

import android.app.Application;
import android.content.Intent;

import com.elkusnandi.popularmovie.data.provider.FavouriteMovieListService;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Intent serviceIntent = new Intent(getApplicationContext(), FavouriteMovieListService.class);
        startService(serviceIntent);
    }
}
